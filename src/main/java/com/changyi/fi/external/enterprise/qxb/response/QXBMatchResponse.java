package com.changyi.fi.external.enterprise.qxb.response;

import java.util.List;
import java.util.Map;

public class QXBMatchResponse {

    public static final String FIELD_ID = "eid";
    public static final String FIELD_NAME = "name";

    private List<Map<String, String>> data;

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

}
