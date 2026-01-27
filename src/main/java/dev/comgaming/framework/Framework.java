package dev.comgaming.framework;

import dev.comgaming.framework.serverhandler.ConsoleHandler;
import dev.comgaming.framework.serverhandler.LogHandler;
import dev.comgaming.framework.serverhandler.Logger;
import dev.comgaming.framework.serverhandler.LogLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Framework {

    private static final ConsoleHandler consoleHandler = new ConsoleHandler();
    private static final LogHandler logHandler = new LogHandler(consoleHandler);

    @Getter
    private static boolean started = false;

    private static final Logger LOG =
            new Logger(logHandler);

    public void init() {
        logHandler.setMinimumLevel(LogLevel.INFO);
        logHandler.setAllowLogging(true);

        started = true;

        LOG.info("core", "Framework initialisiert");
    }

    public static Logger getLogger() {
        return new Logger(logHandler);
    }
}
