package dev.comgaming.framework;

import dev.comgaming.framework.serverhandler.ConsoleHandler;
import dev.comgaming.framework.serverhandler.LogHandler;
import dev.comgaming.framework.utils.InternalMethods;
import lombok.Getter;

public class Framework {

    static ConsoleHandler consoleHandler = new ConsoleHandler();
    @Getter
    static LogHandler logHandler = new LogHandler(consoleHandler);
    @Getter
    static InternalMethods serverAPI = new InternalMethods();

    public void init(){
        consoleHandler.setWarning(InternalMethods.getTimeForConsole() + "[WARN]");
        consoleHandler.setInfo(InternalMethods.getTimeForConsole() + "[INFO]");
        consoleHandler.setError(InternalMethods.getTimeForConsole() + "[ERROR]");
        logHandler.startLogging();
    }

}
