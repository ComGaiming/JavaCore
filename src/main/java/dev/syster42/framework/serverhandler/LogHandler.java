package dev.syster42.framework.serverhandler;

import dev.syster42.framework.Framework;
import dev.syster42.framework.utils.FileAPI;

public class LogHandler {

    FileAPI logfile = new FileAPI("logs\\latest.log");
    FileAPI logDirectory = new FileAPI(null);
    private final boolean allowLogging;
    ConsoleHandler ch;

    public LogHandler(ConsoleHandler consoleHandler){
        this.allowLogging = true;
        logDirectory = new FileAPI("logs\\");
        this.ch = consoleHandler;
    }

    public LogHandler(ConsoleHandler consoleHandler, boolean allowLogging){
        this.ch = consoleHandler;
        this.allowLogging = allowLogging;
        if(allowLogging){
            logDirectory = new FileAPI("logs\\");
        }
    }
    public LogHandler(ConsoleHandler consoleHandler, boolean allowLogging, String pathLoggingFiles){
        this.ch = consoleHandler;
        this.allowLogging = allowLogging;
        if(allowLogging){
            logDirectory = new FileAPI(pathLoggingFiles);
        }
    }

    public ConsoleHandler getCh() {
        return ch;
    }

    public boolean isAllowLogging() {
        return allowLogging;
    }

    public void startLogging(){
        if(ch.getInfo() != null ||ch.getWarning() != null ||ch.getError() != null){
            System.err.println("ERROR 1");
            System.err.println("Bitte belege die Prefixe noch mit Zeichenketten. Nutze hierfür den in der Main erstellten Consolehandler mit den Methoden setInfo, setWarning und setError. ");
            System.exit(3);
        }
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
        System.out.println(this.getCh().getInfo() + logMessage);
        if(isAllowLogging()){
            logfile.writeInNextFreeLine(this.getCh().getInfo() + logMessage);
        }
    }

    public void logWarn(String logMessage){
        System.out.println(this.getCh().getWarning() + logMessage);
        if(isAllowLogging()){
            logfile.writeInNextFreeLine(this.getCh().getWarning() + logMessage);
        }
    }

    public void logError(String logMessage){
        logfile.writeInNextFreeLine(this.getCh().getError() + logMessage);
        if(isAllowLogging()){
            logfile.writeInNextFreeLine(this.getCh().getError() + logMessage);
        }
    }

}
