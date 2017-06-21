package com.changyi.fi.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by finley on 1/16/17.
 */
public class LogUtil {

    static final Logger logger  =  LoggerFactory.getLogger(LogUtil.class);

    public static void info(Class clz, String info) {
        logger.info(getClassInfo(clz, info));
    }

    public static void info(Class clz, String format, Object para) {
        logger.info(getClassInfo(clz, format), para);
    }

    public static void debug(Class clz, String info) {
        logger.debug(getClassInfo(clz, info));
    }

    public static void debug(Class clz, String format, Object para) { logger.debug(getClassInfo(clz, format), para); }

    public static void error(Class clz, String info, Throwable t) {
        logger.error(getClassInfo(clz, info), t);
    }

    private static String getClassInfo(Class clz, String info) {
        return "[" + clz.getName() + "]: " + info;
    }

}
