package com.changyi.fi.core;

import com.changyi.fi.core.dao.ConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by finley on 2/14/17.
 */
@Service
public class ConfigService {

    private ConfigDao configDao;

    @Autowired(required = true)
    public void setConfigDao(ConfigDao configDao) {
        this.configDao = configDao;
    }

    public List<Map> getExceptionCode() {
        return configDao.getExceptionCode();
    }

    public List<Map> getSysParameter() {
        return configDao.getSysParameter();
    }

}
