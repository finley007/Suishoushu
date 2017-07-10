package com.changyi.fi.component.enterprise.service;

import com.changyi.fi.component.enterprise.request.GetEnterpriseRequest;
import com.changyi.fi.component.enterprise.response.GetEnterpriseResponse;
import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.external.tianyancha.TianYanChaAPIService;
import com.changyi.fi.external.weixin.WeixinAPIService;
import com.changyi.fi.model.EnterprisePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by finley on 6/21/17.
 */
@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService {

    private static final String FIELD_NAME = "name";
    private static final String FIELD_IS_EXTERNAL = "isExternal";

    @Resource
    private TianYanChaAPIService tianYanChaAPIService;

    private InvoiceDao invoiceDao;

    @Autowired(required = true)
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public MatchEnterpriseResponse matchEnterprise(String key) throws Exception {
        LogUtil.info(this.getClass(), "Execute matchEnterprise service for key: " + key);
        //从内部获取
        List<Map> internalList = this.invoiceDao.matchEnterpriseList(key);
        //从外部获取
        List<Map> externalList = tianYanChaAPIService.matchEnterprise(key);
        //结合结果集
        return new MatchEnterpriseResponse(combineResult(internalList, externalList));
    }

    private List combineResult(List<Map> internal, List<Map> external) {
        List<Map> result = new ArrayList<Map>();
        if (internal.size() > 0) {
            Set<String> set = new HashSet<String>();
            for (Map map : internal) {
                map.put(FIELD_IS_EXTERNAL, "false");
                result.add(map);
                set.add(map.get(FIELD_NAME).toString());
            }
            for (Map map : external) {
                if (!set.contains(map.get(FIELD_NAME).toString())) {
                    map.put(FIELD_IS_EXTERNAL, "true");
                    result.add(map);
                }
            }
        } else {
            for (Map map : external) {
                map.put(FIELD_IS_EXTERNAL, "true");
                result.add(map);
            }
        }
        return result;
    }

    @Validate
    public GetEnterpriseResponse getEnterprise(GetEnterpriseRequest req) throws Exception {
        LogUtil.info(this.getClass(), "Execute getEnterprise service");
        if (!Boolean.valueOf(req.getIsExternal())) {
            return new GetEnterpriseResponse(this.invoiceDao.getEnterpriseById(req.getCreditCode()));
        } else {
            EnterprisePO po = this.tianYanChaAPIService.getEnterpriseByCode(req.getCreditCode());
            if (po != null) {
                this.invoiceDao.insertEnterprise(po);
            }
            return new GetEnterpriseResponse(po);
        }
    }
}
