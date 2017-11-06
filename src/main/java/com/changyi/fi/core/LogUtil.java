package com.changyi.fi.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by finley on 1/16/17.
 */
public class LogUtil {

    static final Logger logger  =  LoggerFactory.getLogger(LogUtil.class);

    static final ThreadLocal<Logger> daemonLogger = new ThreadLocal<Logger>();

    private static Logger getLogger() {
        if (daemonLogger != null && daemonLogger.get() != null) {
            return daemonLogger.get();
        } else {
            return logger;
        }
    }

    public static void initLocalLogger() {
        Logger logger = LoggerFactory.getLogger("DAEMON");
        daemonLogger.set(logger);
    }

    public static void info(Class clz, String info) {
        getLogger().info(getClassInfo(clz, info));
    }

    public static void info(Class clz, String format, Object para) {
        getLogger().info(getClassInfo(clz, format), para);
    }

    public static void debug(Class clz, String info) {
        getLogger().debug(getClassInfo(clz, info));
    }

    public static void debug(Class clz, String format, Object para) {
        getLogger().debug(getClassInfo(clz, format), para);
    }

    public static void error(Class clz, String info, Throwable t) {
        getLogger().error(getClassInfo(clz, info), t);
    }

    public static void warn(Class clz, String info) {
        getLogger().warn(info);
    }

    private static String getClassInfo(Class clz, String info) {
        return "[" + clz.getName() + "]: " + info;
    }

}
