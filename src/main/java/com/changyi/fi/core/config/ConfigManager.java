package com.changyi.fi.core.config;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.tool.DictionaryManager;
import org.apache.commons.lang3.StringUtils;

public class ConfigManager {

    public static String getParameter(String name) {
        if (DictionaryManager.dic(ConfigDic.NAME) == null) {
            DictionaryManager.register(ConfigDic.NAME, new ConfigDic());
        }
        return ((ConfigDic) DictionaryManager.dic(ConfigDic.NAME)).get(name);
    }

    public static Boolean getBooleanParameter(String name, Boolean defaultValue) {
        String value = getParameter(name);
        if (StringUtils.isNotBlank(value) && "true".equalsIgnoreCase(value)) {
            return true;
        } else {
            return defaultValue;
        }
    }

    public static Double getDoubleParameter(String name, Double defaultValue) {
        String value = getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            try {
                return Double.valueOf(value);
            } catch (Exception e) {
                LogUtil.warn(ConfigManager.class, "Invalid parameter value: " + name);
                return defaultValue;
            }
        } else {
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
