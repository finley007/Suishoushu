package com.changyi.fi.external.enterprise;

import com.changyi.fi.model.EnterprisePO;

import java.util.List;
import java.util.Map;

/**
 * Created by finley on 7/8/17.
 */
public interface ExternalEnterpriseAPIService {

    public List<Map> matchEnterprise(String key) throws Exception;

    public EnterprisePO getEnterpriseByCode(String code) throws Exception;



}
