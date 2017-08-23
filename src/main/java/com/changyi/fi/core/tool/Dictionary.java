package com.changyi.fi.core.tool;

import java.util.List;
import java.util.Map;

/**
 * Created by finley on 2/8/17.
 */
public interface Dictionary<T> {

    public T get(String key);

    public boolean conatains(String key);

    public void add(String key, T value);

    public void remove(String key);

    public void removeAll();

    public List<String> listKeys();

    public List<Map<String, T>> listAll();

    public void refresh();

}
