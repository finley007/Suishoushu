package com.changyi.fi.core.tool;

import com.changyi.fi.core.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by finley on 2/8/17.
 */
public class DictionaryManager {

    private static Map<String, Dictionary<? extends Object>> dicRepo_= new HashMap<String, Dictionary<? extends Object>>();

    public static void register(String name, Dictionary<? extends Object> dic) {
        if (!dicRepo_.containsKey(name)) {
            dicRepo_.put(name, dic);
        }
    }

    public static Dictionary<? extends Object> dic(String name) {
        if (dicRepo_.containsKey(name)) {
            return dicRepo_.get(name);
        } else {
            return null;
        }
    }

    public static void refresh() {
        if (dicRepo_ != null && dicRepo_.keySet() != null && dicRepo_.keySet().size() > 0) {
            for (String key : dicRepo_.keySet()) {
                Dictionary dic = dicRepo_.get(key);
                if (dic != null) {
                    LogUtil.info(DictionaryManager.class, "Refresh dictionary: " + key);
                    dic.refresh();
                }
            }
        }
    }
}
