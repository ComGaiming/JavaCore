package dev.syster42.framework.utils;

import dev.syster42.framework.Framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private final FileAPI fileAPI;
    private Connection c = null;

    public DatabaseManager(String filename) {
        fileAPI = new FileAPI(filename);
        if (!fileAPI.exists()) {
            fileAPI.createFile();
            fileAPI.writeInNextFreeLine("databasesoftware: postgresql");
            fileAPI.writeInNextFreeLine("database: database");
            fileAPI.writeInNextFreeLine("host: 127.0.0.1");
            fileAPI.writeInNextFreeLine("port: 5432");
            fileAPI.writeInNextFreeLine("username: username");
            fileAPI.writeInNextFreeLine("password: password");
            fileAPI.writeInNextFreeLine("autoreconect: true");
        }
    }

    public FileAPI getFileAPI() {
        return fileAPI;
    }

    public String getDatabaseSoftware() {
        String databasesoftware = null;
        for (String str : fileAPI.readAll()) {
            if (str.startsWith("databasesoftware")) {
                databasesoftware = str.split(": ")[1];
            }
        }
        return databasesoftware;
    }

    public String getDatabase() {
        String database = fileAPI.readLine(2);
        return database.split(": ")[1];
    }

    public String getHost() {
        String host = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("host")) {
                host = str.split(": ")[1];
            }
        }
        return host;
    }

    public String getPort() {
        String port = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("port")) {
                port = str.split(": ")[1];
            }
        }
        return port;
    }

    public int getPortAsInt() {
        String port = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("port")) {
                port = str.split(": ")[1];
            }
        }
        assert port != null;
        return Integer.parseInt(port);
    }

    public String getDatabaseUsername() {
        String username = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("username")) {
                username = str.split(": ")[1];
            }
        }
        return username;
    }

    public String getDatabasePassword() {
        String password = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("password")) {
                password = str.split(": ")[1];
            }
        }
        return password;
    }

    public void connect() {
        assert this.getDatabaseSoftware() != null;

        if (getDatabaseSoftware().contains("postgresql")) {
            try {
                final String url = "jdbc:postgresql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDatabase();
                c = DriverManager.getConnection (url, this.getDatabaseUsername(), this.getDatabasePassword());
                Framework.getLogHandler().logInfo("Connected to Database " + this.getDatabase() + "!");
            } catch (Exception e) {
                e.printStackTrace();
                Framework.getLogHandler().logError(e.getClass().getName() + ": " + e.getMessage());
                System.exit(3);
            }
        } else if (getDatabaseSoftware().contains("mariadb")) {
            final String url="jdbc:mariadb://" + this.getHost() + ":" + getPort() + "/" + getDatabase();
            String driver="org.mariadb.jdbc.Driver";
            try {
                Class.forName(driver);
                c=DriverManager.getConnection(url, this.getDatabaseUsername(), this.getDatabasePassword());
                Framework.getLogHandler().logInfo("Connected to Database " + this.getDatabase() + "!");
            } catch (Exception e) {
                e.printStackTrace();
                Framework.getLogHandler().logError(e.getClass().getName() + ": " + e.getMessage());
                System.exit(3);
            }
        } else if (getDatabaseSoftware().contains("mongodb")) {
            final String url="jdbc:mongodb://" + this.getHost() + ":" + getPort() + "/" + getDatabase();
            String driver="org.mongodb.jdbc.Driver";
            try {
                Class.forName(driver);
                c=DriverManager.getConnection(url, this.getDatabaseUsername(), this.getDatabasePassword());
                Framework.getLogHandler().logInfo("Connected to Database " + this.getDatabase() + "!");
            } catch (Exception e) {
                e.printStackTrace();
                Framework.getLogHandler().logError(e.getClass().getName() + ": " + e.getMessage());
                System.exit(3);
            }
        } else if (getDatabaseSoftware().contains("SQLite")) {
            final String url="jdbc:SQLite://" + this.getHost() + ":" + getPort() + "/" + getDatabase();
            String driver="org.SQLite.jdbc.Driver";
            try {
                Class.forName(driver);
                c=DriverManager.getConnection(url, this.getDatabaseUsername(), this.getDatabasePassword());
                Framework.getLogHandler().logInfo("Connected to Database " + this.getDatabase() + "!");
            } catch (Exception e) {
                e.printStackTrace();
                Framework.getLogHandler().logError(e.getClass().getName() + ": " + e.getMessage());
                System.exit(3);
            }
        }
    }

    public Connection getConnection() {
        return c;
    }

    public void disconnect() {
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}