package com.changyi.fi.component.enterprise.response;

import com.changyi.fi.core.response.NormalResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by finley on 6/21/17.
 */
public class MatchEnterpriseResponse extends NormalResponse {

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count = 0;

    public MatchEnterpriseResponse(List<Map> list) {
        this.enterpriseList = list;
        this.count = list.size();
    }

    public List<Map> getEnterpriseList() {
        return enterpriseList;
    }

    public void setEnterpriseList(List<Map> enterpriseList) {
        this.enterpriseList = enterpriseList;
    }

    private List<Map> enterpriseList;
}
