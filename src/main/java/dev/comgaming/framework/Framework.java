package dev.comgaming.framework;

import dev.comgaming.framework.serverhandler.ConsoleHandler;
import dev.comgaming.framework.serverhandler.LogHandler;
import dev.comgaming.framework.api.ServerAPI;

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
