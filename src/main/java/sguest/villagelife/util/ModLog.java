package sguest.villagelife.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sguest.villagelife.VillageLife;

public class ModLog {
    public static Logger getLogger() {
        return LogManager.getLogger(VillageLife.MOD_ID);
    }

    public static void info(String message) {
        getLogger().info(message);
    }

    public static void info(String message, Object ... params) {
        getLogger().info(message, params);
    }

    public static void info(Object message) {
        getLogger().info(message);
    }

    public static void error(String message, Object ... args) {
        getLogger().error(message, args);
    }
}
