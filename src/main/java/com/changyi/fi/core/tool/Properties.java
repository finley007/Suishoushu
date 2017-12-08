package com.changyi.fi.core.tool;


import com.changyi.fi.core.LogUtil;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.util.List;

/**
 * Created by finley on 1/30/17.
 */
public class Properties {

    private static final String CONFIGURATION_PROP = "/Users/finley/Finley/workspace/java/Suishoushu/src/main/profile/local/config.properties";

    private static PropertiesConfiguration propertiesConfiguration = null;

    static {
        try {
            setPropertiesConfiguration(new PropertiesConfiguration(CONFIGURATION_PROP));
        } catch (ConfigurationException e) {
            LogUtil.error(Properties.class, "Init properties: ", e);
        }
    }

    public static void setPropertiesConfiguration(PropertiesConfiguration config) {
        propertiesConfiguration = config;
        propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

    public static String get(String propertyName){
        return propertiesConfiguration.getString(propertyName);
    }

    public static List getPropertyList(String propertyName){
        return propertiesConfiguration.getList(propertyName);
    }

}
