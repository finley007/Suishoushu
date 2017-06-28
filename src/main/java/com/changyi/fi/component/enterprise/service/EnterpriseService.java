package com.changyi.fi.component.enterprise.service;

import com.changyi.fi.component.enterprise.response.GetEnterpriseResponse;
import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;

/**
 * Created by finley on 6/21/17.
 */
public interface EnterpriseService {

    public MatchEnterpriseResponse matchEnterprise(String key) throws Exception;

    public GetEnterpriseResponse getEnterprise(String creditCode) throws Exception;
}
