package com.changyi.fi.component.enterprise.response;

import com.changyi.fi.core.Payload;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.model.EnterprisePO;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GetEnterpriseResponse extends NormalResponse {

    public GetEnterpriseResponse(EnterprisePO enterprise) {
        this.enterprise = enterprise;
    }

    public EnterprisePO getEnterprisePO() {
        return enterprise;
    }

    public void setEnterprisePO(EnterprisePO enterprise) {
        this.enterprise = enterprise;
    }

    private EnterprisePO enterprise;

    public String build() {
        if (this.enterprise == null) {
            return "{ \"returnCode\" : \"0\", \"content\" : \"\" }";
        }
        String content = new Payload(this.enterprise).setExclusion(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("createTime")
                        || f.getName().contains("createBy")
                            || f.getName().contains("modifyTime")
                                || f.getName().contains("modifyBy");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).from(this.enterprise.getClass());
        if (!"{}".equals(content)) {
            return "{ \"returnCode\" : \"0\", \"content\" : " + content + " }";
        } else {
            return "{ \"returnCode\" : \"0\", \"content\" : \"success\" }";
        }
    }

}
