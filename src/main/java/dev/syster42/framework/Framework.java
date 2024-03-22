package dev.syster42.framework;

import dev.syster42.framework.api.ServerAPI;
import dev.syster42.framework.serverhandler.LogHandler;

public class Framework {

    public static ServerAPI serverAPI = new ServerAPI();
    public static LogHandler logHandler = new LogHandler();

    public static ServerAPI getServerAPI() {
        return serverAPI;
    }

    public static LogHandler getLogHandler() {
        return logHandler;
    }
}
