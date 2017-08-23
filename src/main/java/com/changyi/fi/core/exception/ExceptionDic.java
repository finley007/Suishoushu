package com.changyi.fi.core.exception;

import com.changyi.fi.core.ConfigService;
import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.core.tool.Dictionary;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by finley on 2/8/17.
 */
public class ExceptionDic implements Dictionary<String> {

    public static final String NAME = "exception";

    private static final String FIELD_CLASS = "CLASS";
    private static final String FIELD_CODE = "CODE";

    public ExceptionDic() {
        this.refresh();
    }

    private Map<String, String> exDic_ = new HashMap<String, String>();

    public String get(String key) {
        String value = exDic_.get(key);
        return value == null ? "" : value;
    }

    public boolean conatains(String key) {
        String value = exDic_.get(key);
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
        List<Map> list = configService.getExceptionCode();
        exDic_ = new HashMap<String, String>();
        for (Map map : list) {
            exDic_.put(map.get(FIELD_CLASS).toString(), map.get(FIELD_CODE).toString());
        }
    }
}
