package com.changyi.fi.core.config;

import com.changyi.fi.core.ConfigService;
import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.core.tool.Dictionary;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigDic implements Dictionary<String> {

    public static final String NAME = "config";

    private static final String FIELD_CODE = "code";
    private static final String FIELD_VALUE = "value";

    public ConfigDic() {
        refresh();
    }

    private Map<String, String> cfgDic_ = new HashMap<String, String>();

    public String get(String key) {
        String value = cfgDic_.get(key);
        return value == null ? "" : value;
    }

    public boolean conatains(String key) {
        String value = cfgDic_.get(key);
        return StringUtils.isNotBlank(value);
    }

    public void add(String key, String value) {

    }

    public void remove(String key) {

    }

    public void removeAll() {

    }

    public List<String> listKeys() {
        return null;
    }

    public List<Map<String, String>> listAll() {
        return null;
    }

    public void refresh() {
        ConfigService configService = CtxProvider.getContext().getBean(ConfigService.class);
        List<Map> list = configService.getSysParameter();
        cfgDic_ = new HashMap<String, String>();
        for (Map map : list) {
            cfgDic_.put(map.get(FIELD_CODE).toString(), map.get(FIELD_VALUE).toString());
        }
    }

}
