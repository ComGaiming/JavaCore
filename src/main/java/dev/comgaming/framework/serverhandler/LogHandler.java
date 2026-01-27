package dev.comgaming.framework.serverhandler;

import dev.comgaming.framework.Framework;
import dev.comgaming.framework.utils.filemanager.DirectoryHandler;
import dev.comgaming.framework.utils.filemanager.FileManager;

public class LogHandler {

    private final ConsoleHandler consoleHandler;
    private final FileManager logfile;
    private final DirectoryHandler directoryHandler;

    private boolean allowLogging = true;
    private LogLevel minimumLevel = LogLevel.INFO;

    public static int numberOfLogHandlers = 0;

    public LogHandler(ConsoleHandler consoleHandler) {
        this.consoleHandler = consoleHandler;
        this.logfile = new FileManager("latest.log");
        this.directoryHandler = new DirectoryHandler();

        setupLogDirectory();
        numberOfLogHandlers++;
    }

    private void setupLogDirectory() {
        String path = "logs\\";
        directoryHandler.setPath(path);

        if (!directoryHandler.existsDirectory(path)) {
            directoryHandler.generateDirectory(path);
        }

        logfile.renameFile(path + "latest.log");
    }

    public void setMinimumLevel(LogLevel level) {
        this.minimumLevel = level;
    }

    public void setAllowLogging(boolean allowLogging) {
        this.allowLogging = allowLogging;
    }

    public void log(LogLevel level, String source, String message) {
        if (!Framework.isStarted()) return;
        if (level.ordinal() < minimumLevel.ordinal()) return;

        String line = consoleHandler.format(level, source, message);

        System.out.println(line);

        if (allowLogging) {
            logfile.writeInNextFreeLine(line);
        }
    }

    public void logError(String source, Exception exception) {
        log(LogLevel.ERROR, source, exception.toString());

        if (allowLogging) {
            for (StackTraceElement element : exception.getStackTrace()) {
                logfile.writeInNextFreeLine("\t" + element.toString());
            }
        }
    }
}
