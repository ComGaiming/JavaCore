package dev.syster42.framework.serverhandler;

import dev.syster42.framework.Framework;

public class ConsoleHandler {

    private String error;
    private String warning;
    private String info;

    public String getError(){
        return Framework.getServerAPI().getTimeForConsole() + this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getWarning() {
        return Framework.getServerAPI().getTimeForConsole() + this.warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getInfo() {
        return Framework.getServerAPI().getTimeForConsole() + this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
