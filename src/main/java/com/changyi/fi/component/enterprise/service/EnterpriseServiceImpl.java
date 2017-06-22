package com.changyi.fi.component.enterprise.service;

import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import org.springframework.stereotype.Service;

/**
 * Created by finley on 6/21/17.
 */
@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService {

    public MatchEnterpriseResponse matchEnterprise(String key) {
        return new MatchEnterpriseResponse();
    }
}
