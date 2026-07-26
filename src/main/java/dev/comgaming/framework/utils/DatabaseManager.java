package dev.comgaming.framework.utils;

import dev.comgaming.framework.Framework;
import dev.comgaming.framework.utils.filemanager.FileManager;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

@Getter
public class DatabaseManager {

    private final FileManager fileAPI;
    private Connection connection = null;

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private String driverClassName;

    /**
     * Konstruktor
     * @param filename YAML-Datei z.B. "backend"
     * @param isSetupable true, wenn Setup durch User-Eingabe erlaubt ist
     */
    public DatabaseManager(String filename, boolean isSetupable) {
        fileAPI = new FileManager(filename + ".yml");

        if (!fileAPI.exists()) {
            if (isSetupable) {
                setupInteractive();
            } else {
                writeDefaultConfig();
            }
        }

        loadConfig();
    }

    /**
     * Interaktives Setup über Konsole
     */
    private void setupInteractive() {
        Scanner scanner = new Scanner(System.in);

        Framework.getLogger().info("databasesetup", "Which database would you like to use?");
        database = scanner.nextLine();
        Framework.getLogger().info("databasesetup", "Which host would you like to use?");
        host = scanner.nextLine();
        Framework.getLogger().info("databasesetup", "Which port would you like to use?");
        port = scanner.nextLine();
        Framework.getLogger().info("databasesetup", "Which user would you like to use?");
        username = scanner.nextLine();
        Framework.getLogger().info("databasesetup", "Which password would you like to use?");
        password = scanner.nextLine();
        scanner.close(); // Achtung: danach kann System.in nicht mehr gelesen werden

        driverClassName = "org.mariadb.jdbc.Driver";

        // YAML-Datei erstellen
        fileAPI.createFile();
        fileAPI.writeInNextFreeLine("database: " + database);
        fileAPI.writeInNextFreeLine("host: " + host);
        fileAPI.writeInNextFreeLine("port: " + port);
        fileAPI.writeInNextFreeLine("username: " + username);
        fileAPI.writeInNextFreeLine("password: " + password);
        fileAPI.writeInNextFreeLine("driver-class-name: " + driverClassName);
    }

    /**
     * Default Config schreiben
     */
    private void writeDefaultConfig() {
        database = "database";
        host = "127.0.0.1";
        port = "3306";
        username = "username";
        password = "password";
        driverClassName = "org.mariadb.jdbc.Driver";

        fileAPI.createFile();
        fileAPI.writeInNextFreeLine("database: " + database);
        fileAPI.writeInNextFreeLine("host: " + host);
        fileAPI.writeInNextFreeLine("port: " + port);
        fileAPI.writeInNextFreeLine("username: " + username);
        fileAPI.writeInNextFreeLine("password: " + password);
        fileAPI.writeInNextFreeLine("driver-class-name: " + driverClassName);
    }

    /**
     * Config aus YAML einlesen
     */
    private void loadConfig() {
        try (InputStream in = new FileInputStream(fileAPI.getFile())) {
            Yaml yaml = new Yaml();
            Map<String, Object> obj = yaml.load(in);

            database = obj.get("database").toString();
            host = obj.get("host").toString();
            port = obj.get("port").toString();
            username = obj.get("username").toString();
            password = obj.get("password").toString();
            driverClassName = obj.getOrDefault("driver-class-name", "org.mariadb.jdbc.Driver").toString();

        } catch (Exception e) {
            Framework.getLogger().error("database-exception", "Fehler beim Laden der DB Config: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Verbindung zur Datenbank herstellen
     */
    public void connect() {
        String urlWithoutDb = "jdbc:mariadb://" + host + ":" + port + "/";
        String urlWithDb = "jdbc:mariadb://" + host + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true";

        try {
            Class.forName(driverClassName);

            // Verbindung ohne DB für CREATE DATABASE
            try (Connection temp = DriverManager.getConnection(urlWithoutDb, username, password)) {
                temp.createStatement().execute(
                        "CREATE DATABASE IF NOT EXISTS `" + database + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci"
                );
            }

            // Verbindung direkt auf DB
            connection = DriverManager.getConnection(urlWithDb, username, password);
            Framework.getLogger().info("database", "Connected to Database " + database + "!");

        } catch (Exception e) {
            Framework.getLogger().error("database-exception", e.getClass().getName() + ": " + e.getMessage());
            System.exit(3);
        }
    }

    /**
     * Aktive Verbindung zurückgeben
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            Framework.getLogger().error("database", e.getClass().getName() + ": " + e.getMessage());
        }
        return connection;
    }

    /**
     * Verbindung schließen
     */
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Framework.getLogger().info("database", "Disconnected from Database " + database + "!");
            }
        } catch (SQLException e) {
            Framework.getLogger().error("database", e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
