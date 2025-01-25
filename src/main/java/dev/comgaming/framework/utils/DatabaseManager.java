package dev.comgaming.framework.utils;

import dev.comgaming.framework.Framework;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DatabaseManager {

    private final FileManager fileAPI;
    private Connection c = null;

    public DatabaseManager(String filename) {
        fileAPI = new FileManager(filename+ ".yml");
        if (!fileAPI.exists()) {
            fileAPI.createFile();
            fileAPI.writeInNextFreeLine("database: database");
            fileAPI.writeInNextFreeLine("host: 127.0.0.1");
            fileAPI.writeInNextFreeLine("port: 3306");
            fileAPI.writeInNextFreeLine("username: username");
            fileAPI.writeInNextFreeLine("password: password");
        }
    }

    public String getProperty(String key) {
        for (String str : fileAPI.readAll()) {
            if (str.startsWith(key + ": ")) {
                return str.split(": ")[1];
            }
        }
        return null;
    }

    public String getDatabase() {
        return getProperty("database");
    }

    public String getHost() {
        return getProperty("host");
    }

    public String getPort() {
        return getProperty("port");
    }

    public String getDatabaseUsername() {
        return getProperty("username");
    }

    public String getDatabasePassword() {
        return getProperty("password");
    }

    public void connect() {
        final String url="jdbc:mariadb://" + this.getHost() + ":" + this.getPort() + "/" + getDatabase();
        String driver="org.mariadb.jdbc.Driver";
        try {
            Class.forName(driver);
            c=DriverManager.getConnection(url, getDatabaseUsername(), getDatabasePassword());
            Framework.getLogHandler().logInfo("Connected to Database " + this.getDatabase() + "!");
        } catch (Exception e) {
            Framework.getLogHandler().logError(e.getClass().getName() + ": " + e.getMessage());
            System.exit(3);
        }
    }

    public Connection getConnection() {
        try {
            if (c == null || c.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            Framework.getLogHandler().logError(e.getClass().getName() + ": " + e.getMessage());
        }
        return c;
    }

    public void disconnect() {
        try {
            if(c!= null){
                c.close();
            }
            Framework.getLogHandler().logInfo("Disconnected from Database " + this.getDatabase() + "!");
        } catch (SQLException e) {
            Framework.getLogHandler().logError(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}