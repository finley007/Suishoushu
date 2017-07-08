package com.changyi.fi.external.tianyancha;

import com.changyi.fi.model.EnterprisePO;

import java.util.List;
import java.util.Map;

/**
 * Created by finley on 7/8/17.
 */
public interface TianYanChaAPIService {

    public List<Map> matchEnterprise(String key) throws Exception;

    public EnterprisePO getEnterpriseByCode(String code) throws Exception;

}
