package com.twitter.config;

import java.util.logging.*;

public class CustomLogger extends Formatter {

    @Override
    public synchronized String format(LogRecord record) {
        return record.getMessage() + "\n";
    }

    public static Logger getLogger(Class clazz) {
        final Logger logger = Logger.getLogger(clazz.getName());
        CustomLogger formatter = new CustomLogger();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);
        consoleHandler.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        return logger;
    }
}
