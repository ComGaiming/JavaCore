package dev.syster42.framework.serverhandler;

import dev.syster42.framework.Framework;
import dev.syster42.framework.utils.FileAPI;

public class LogHandler {

    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

    public static ConsoleHandler getConsoleHandler() {
        return consoleHandler;
    }

    FileAPI logfile = new FileAPI("logs\\latest.log");
    FileAPI logDirectory = new FileAPI(null);
    private final boolean allowLogging;


    public LogHandler(){
        this.allowLogging = true;
        logDirectory = new FileAPI("logs\\");
    }

    public LogHandler(boolean allowLogging){
        this.allowLogging = allowLogging;
        if(allowLogging){
            logDirectory = new FileAPI("logs\\");
        }
    }
    public LogHandler(boolean allowLogging, String pathLoggingFiles){
        this.allowLogging = allowLogging;
        if(allowLogging){
            logDirectory = new FileAPI(pathLoggingFiles);
        }
    }

    public void startLogging(){
        if(isAllowLogging()){
            if(logfile.exists()){
                logfile.renameFile(Framework.getServerAPI().getTimeForFiles() + ".log");
                logfile.createFile();
            }else{
                logfile.createFile();
                if(!logDirectory.exists()){
                    logDirectory.getFile().mkdirs();
                }
            }
        }
    }

    public void logInfo(String logMessage){
        getConsoleHandler().setInfo("[INFO] ");
        System.out.println(getConsoleHandler().getInfo() + logMessage);
        if(isAllowLogging()){
            logfile.writeInNextFreeLine(getConsoleHandler().getInfo() + logMessage);
        }
    }

    public void logWarn(String logMessage){
        getConsoleHandler().setWarning("[WARN] ");
        System.out.println(getConsoleHandler().getWarning() + logMessage);
        if(isAllowLogging()){
            logfile.writeInNextFreeLine(getConsoleHandler().getWarning() + logMessage);
        }
    }

    public void logError(String logMessage){
        getConsoleHandler().setInfo("[ERROR] ");
        logfile.writeInNextFreeLine(getConsoleHandler().getError() + logMessage);
        if(isAllowLogging()){
            logfile.writeInNextFreeLine(getConsoleHandler().getError() + logMessage);
        }
    }

    public boolean isAllowLogging() {
        return allowLogging;
    }
}
