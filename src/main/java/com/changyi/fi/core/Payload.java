package com.changyi.fi.core;

import com.changyi.fi.core.exception.SystemException;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang.StringUtils;

/**
 * Created by finley on 2/6/17.
 */
public class Payload {

    private String payload;
    private Object bean;

    private ExclusionStrategy exclusion;

    public Payload setExclusion(ExclusionStrategy exclusion) {
        this.exclusion = exclusion;
        return this;
    }

    public Payload(String payload) throws Exception {
        if (StringUtils.isEmpty(payload)) {
            throw new SystemException("Payload is empty");
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
            throw new SystemException("Invalid format json: " + payload);
        }
    }

    public <T> String from(Class<T> clz) {
        if (this.exclusion == null) {
            return new Gson().toJson(bean, clz);
        } else {
            return new GsonBuilder().setExclusionStrategies(this.exclusion).create().toJson(bean, clz);
        }
    }

}
