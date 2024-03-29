package dev.syster42.framework;

import dev.syster42.framework.api.ServerAPI;
import dev.syster42.framework.serverhandler.ConsoleHandler;
import dev.syster42.framework.serverhandler.LogHandler;

import javax.swing.*;

public class Framework {

    static ConsoleHandler consoleHandler = new ConsoleHandler();
    static LogHandler logHandler = new LogHandler(consoleHandler);
    static ServerAPI serverAPI = new ServerAPI();

    public static ServerAPI getServerAPI() {
        return serverAPI;
    }

    public void init(){
        consoleHandler.setWarning(serverAPI.getTimeForConsole() + "[WARN]");
        consoleHandler.setInfo(serverAPI.getTimeForConsole() + "[INFO]");
        consoleHandler.setError(serverAPI.getTimeForConsole() + "[ERROR]");
        logHandler.startLogging();
    }

    public static LogHandler getLogHandler() {
        return logHandler;
    }
}
