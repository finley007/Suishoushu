package com.changyi.fi.core.tool;

import java.util.HashMap;
import java.util.Map;

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
}
