package com.changyi.fi.core.config;

import com.changyi.fi.core.tool.DictionaryManager;

public class ConfigManager {

    public static String getParameter(String name) {
        if (DictionaryManager.dic(ConfigDic.NAME) == null) {
            DictionaryManager.register(ConfigDic.NAME, new ConfigDic());
        }
        return ((ConfigDic) DictionaryManager.dic(ConfigDic.NAME)).get(name);
    }
}
