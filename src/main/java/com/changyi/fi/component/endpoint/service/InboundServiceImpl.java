package com.changyi.fi.component.endpoint.service;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.dao.ConfigDao;
import com.changyi.fi.dao.CustomerDao;
import com.changyi.fi.model.CustomerLoginPO;
import com.changyi.fi.model.CustomerPO;
import com.changyi.fi.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("inboundService")
public class InboundServiceImpl implements InboundService {

    private ConfigDao configDao;

    @Autowired(required = true)
    public void setConfigDao(ConfigDao configDao) {
        this.configDao = configDao;
    }

    @Validate
    public void updateParameterByCode(String code, String value) throws Exception {
        LogUtil.info(this.getClass(), "Update system parameter");
        configDao.updateParameterByCode(code, value);
    }
}
