package dev.comgaming.framework.serverhandler;

import dev.comgaming.framework.utils.FileManager;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class LogHandler {

    FileManager logfile = new FileManager("latest.log");
    FileManager logDirectory;

    private final boolean allowLogging;
    ConsoleHandler ch = new ConsoleHandler();

    public LogHandler(ConsoleHandler consoleHandler){
        this.allowLogging = true;
        logDirectory = new FileManager("logs");
        this.ch.setInfo("[INFO]");
        this.ch.setError("[ERROR]");
        this.ch.setWarning("[WARN]");
    }

    public void startLogging(){
        if(isAllowLogging()){
            if(logfile.exists()){
                logfile.renameFile("logs/"+getTimeForFiles() + ".log");
                logfile.createFile();
            }
        }
    }

    public void logInfo(String logMessage){
        System.out.println(this.ch.getInfo() + logMessage);
        if(isAllowLogging()){
            logfile.writeInNextFreeLine(this.ch.getInfo() + logMessage);
        }
    }

    public void logWarn(String logMessage){
        System.out.println(this.ch.getWarning() + logMessage);
        if(isAllowLogging()){
            logfile.writeInNextFreeLine(this.ch.getWarning() + logMessage);
        }
    }

    public void logError(String logMessage){
        logfile.writeInNextFreeLine(this.ch.getError() + logMessage);
        if(isAllowLogging()){
            logfile.writeInNextFreeLine(this.ch.getError() + logMessage);
        }
    }

    public String getTimeForFiles() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
        return sdf.format(new Date());
    }

}