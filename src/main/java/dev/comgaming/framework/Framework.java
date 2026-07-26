package dev.comgaming.framework;

import dev.comgaming.framework.serverhandler.ConsoleHandler;
import dev.comgaming.framework.serverhandler.LogHandler;
import dev.comgaming.framework.serverhandler.Logger;
import dev.comgaming.framework.serverhandler.LogLevel;
import lombok.Getter;

@Getter
public class Framework {

    private static ConsoleHandler consoleHandler;
    private static LogHandler logHandler;
    private static Logger logger;

    @Getter
    private static boolean started = false;

    public void init() {
        if (started) return;

        started = true;


        consoleHandler = new ConsoleHandler();
        logHandler = new LogHandler(consoleHandler);

        logHandler.setMinimumLevel(LogLevel.INFO);
        logHandler.setAllowLogging(true);

        logger = new Logger(logHandler);


        logger.info("core", "Framework initialisiert");
    }

    public static Logger getLogger() {
        if (!started) {
            throw new IllegalStateException(
                    "Framework not initialized. Call Framework.init() first."
            );
        }
        return logger;
    }
}
