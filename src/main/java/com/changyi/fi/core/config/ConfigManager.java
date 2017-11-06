package com.changyi.fi.core.config;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.tool.DictionaryManager;
import org.apache.commons.lang3.StringUtils;

public class ConfigManager {

    public static final String ENTERPRISE_EXTERNAL_SERVICE_TOGGLE = "ENTERPRISE_EXTERNAL_SERVICE_TOGGLE";
    public static final String ENTERPRISE_MATCH_RESULT_LENGTH = "ENTERPRISE_MATCH_RESULT_LENGTH";
    public static final String ENTERPRISE_EXTERNAL_SERVICE_IMPL = "ENTERPRISE_EXTERNAL_SERVICE_IMPL";
    public static final String SYNC_ENTERPRISE_WHEN_MATCH = "SYNC_ENTERPRISE_WHEN_MATCH";
    public static final String HTTP_PROXY_TOGGLE = "HTTP_PROXY_TOGGLE";
    public static final String HTTP_TIMEOUT = "HTTP_TIMEOUT";
    public static final String MERCHANT_VALID_DISTANCE = "MERCHANT_VALID_DISTANCE";
    public static final String MERCHANT_VALIDATION_TOGGLE = "MERCHANT_VALIDATION_TOGGLE";
    public static final String JOB_THREAD_POOL_SIZE = "JOB_THREAD_POOL_SIZE";

    public static String getParameter(String name) {
        if (DictionaryManager.dic(ConfigDic.NAME) == null) {
            DictionaryManager.register(ConfigDic.NAME, new ConfigDic());
        }
        return ((ConfigDic) DictionaryManager.dic(ConfigDic.NAME)).get(name);
    }

    public static Boolean getBooleanParameter(String name, Boolean defaultValue) {
        String value = getParameter(name);
        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            LogUtil.warn(ConfigManager.class, "Invalid parameter value: " + name);
            return defaultValue;
        }
    }

    public static Double getDoubleParameter(String name, Double defaultValue) {
        String value = getParameter(name);
        try {
            return Double.valueOf(value);
        } catch (Exception e) {
            LogUtil.warn(ConfigManager.class, "Invalid parameter value: " + name);
            return defaultValue;
        }
    }

    public static Integer getIntegerParameter(String name, Integer defaultValue) {
        String value = getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            try {
                return Integer.valueOf(value);
            } catch (Exception e) {
                LogUtil.warn(ConfigManager.class, "Invalid parameter value: " + name);
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }


}
