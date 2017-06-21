package com.changyi.fi.core;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mysql.cj.core.util.StringUtils;

/**
 * Created by finley on 2/6/17.
 */
public class Payload {

    private String payload;
    private Object bean;

    public Payload(String payload) throws Exception {
        if (StringUtils.isNullOrEmpty(payload) || StringUtils.isNullOrEmpty(payload.trim())) {
            throw new Exception();
        }
        this.payload = payload;
    }

    public Payload(Object bean) {
        this.bean = bean;
    }

    public <T> T as(Class<T> clz) throws Exception {
        try {
            return new Gson().fromJson(payload, clz);
        } catch (JsonSyntaxException e) {
            throw new Exception();
        }
    }

    public <T> String from(Class<T> clz) {
        return new Gson().toJson(bean, clz);
    }

}
