package com.changyi.fi.core;

import com.changyi.fi.core.dao.ConfigDao;
import com.changyi.fi.core.dao.SysDao;
import com.changyi.fi.core.model.SysServImplPO;
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

    private SysDao sysDao;

    @Autowired(required = true)
    public void setConfigDao(ConfigDao configDao) {
        this.configDao = configDao;
    }

    @Autowired(required = true)
    public void setSysDao(SysDao sysDao) {this.sysDao = sysDao; }

    public List<Map> getExceptionCode() {
        return configDao.getExceptionCode();
    }

    public List<Map> getSysParameter() {
        return configDao.getSysParameter();
    }

    public List<SysServImplPO> getSysServImplsByType(String type) { return sysDao.getSysServImplsByType(type); }

    public int updateSysServImplWeight(String id, int weight) {
        SysServImplPO po = new SysServImplPO();
        po.setId(id);
        po.setWeight(weight);
        return sysDao.updateSysServImpl(po);
    }

}
