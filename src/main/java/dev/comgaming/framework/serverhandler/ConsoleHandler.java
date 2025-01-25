package dev.comgaming.framework.serverhandler;

import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
public class ConsoleHandler {

    private String error;
    private String warning;
    private String info;

    public String getError(){
        return getTimeForConsole() + this.error;
    }

    public String getWarning() {
        return getTimeForConsole() + this.warning;
    }

    public String getInfo() {
        return getTimeForConsole() + this.info;
    }

    public String getTimeForConsole() {
        SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss] ");
        return sdf.format(new Date());
    }

}