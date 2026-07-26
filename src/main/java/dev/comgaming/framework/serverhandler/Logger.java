package dev.comgaming.framework.serverhandler;

public class Logger {

    private final LogHandler logHandler;

    public Logger(LogHandler logHandler) {
        this.logHandler = logHandler;
    }

    public void info(String source, String message) {
        logHandler.log(LogLevel.INFO, source, message);
    }

    public void warn(String source, String message) {
        logHandler.log(LogLevel.WARN, source, message);
    }

    public void error(String source, String message) {
        logHandler.log(LogLevel.ERROR, source, message);
    }

    public void debug(String source, String message) {
        logHandler.log(LogLevel.DEBUG, source, message);
    }

    public void error(String source, Exception exception) {
        logHandler.logError(source, exception);
    }
}
