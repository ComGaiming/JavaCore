package dev.comgaming.framework.serverhandler;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsoleHandler {

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("[HH:mm:ss]");

    public String format(LogLevel level, String source, String message) {
        return getTime()
                + " [" + level + "]"
                + " [Source: " + source + "] "
                + message;
    }

    private String getTime() {
        return LocalTime.now().format(TIME_FORMAT);
    }
}
