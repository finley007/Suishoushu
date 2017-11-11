package com.changyi.fi.core;

import com.changyi.fi.core.config.ConfigDic;
import com.changyi.fi.core.config.ConfigManager;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Collection;

/**
 * Created by finley on 1/16/17.
 */
public class LogUtil {

    public final static String LOGGER_ROOT = "Root";

    public final static String DAEMON_THREAD = "daemon";
    public final static String LOG_ROUTE_KEY = "ROUTINGKEY";
    public final static String LOG_SEQUENCE = "SEQUENCE";

    private final static int SEQUENCE_LENGTH = 10;

    public static synchronized String createSeq() {
        String seq = "";
        for (int i = 0; i < SEQUENCE_LENGTH; i++) {
            seq += String.valueOf((int)(10*(Math.random())));
        }
        return seq;
    }

    public static void refreshLogLevel() {
        String level = ConfigManager.getParameter(ConfigDic.LOG_LEVEL);
        if (StringUtils.isNotBlank(level)) {
            Collection<org.apache.logging.log4j.core.Logger> notCurrentLoggerCollection = org.apache.logging.log4j.core.LoggerContext.getContext(false).getLoggers();
            Collection<org.apache.logging.log4j.core.Logger> currentLoggerCollection = org.apache.logging.log4j.core.LoggerContext.getContext().getLoggers();
            Collection<org.apache.logging.log4j.core.Logger> loggerCollection = notCurrentLoggerCollection;
            loggerCollection.addAll(currentLoggerCollection);
            for (org.apache.logging.log4j.core.Logger log :loggerCollection){
                log.setLevel(org.apache.logging.log4j.Level.toLevel(level));
            }
        }
    }

    public static synchronized void intSquence() {
        MDC.clear();
        MDC.put(LOG_SEQUENCE, createSeq());

    }

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

    public static void debug(Class clz, String format, Object para) {
        logger.debug(getClassInfo(clz, format), para);
    }

    public static void error(Class clz, String info, Throwable t) {
        logger.error(getClassInfo(clz, info), t);
    }

    public static void warn(Class clz, String info) {logger.warn(info);}

    private static String getClassInfo(Class clz, String info) {
        return "[" + clz.getName() + "]: " + info;
    }

}
