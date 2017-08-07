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

    private static final String CONFIGURATION_PROP = "profile/dev/config.properties";

    private static PropertiesConfiguration propertiesConfiguration = null;

    static {
        try {
            propertiesConfiguration = new PropertiesConfiguration(CONFIGURATION_PROP);
            propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException e) {
            LogUtil.error(Properties.class, "Init properties: ", e);
        }
    }

    public static String get(String propertyName){
        return propertiesConfiguration.getString(propertyName);
    }

    public static List getPropertyList(String propertyName){
        return propertiesConfiguration.getList(propertyName);
    }

}
