package com.changyi.fi.component.statistics.service;

import com.changyi.fi.component.statistics.request.CustomerStatRequest;
import com.changyi.fi.component.statistics.request.EnterpriseStatRequest;
import com.changyi.fi.component.statistics.request.SystemStatRequest;
import com.changyi.fi.component.statistics.response.CustomerStatResponse;
import com.changyi.fi.component.statistics.response.EnterpriseStatResponse;
import com.changyi.fi.component.statistics.response.SystemStatResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.dao.StatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    private StatisticsDao statisticsDao;

    @Autowired(required = true)
    public void setStatisticsDao(StatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    public SystemStatResponse getSystemStatInfo(SystemStatRequest request) throws Exception {
        LogUtil.info(this.getClass(), "Execute getSystemStatInfo service");
        SystemStatResponse res = new SystemStatResponse();
        res.setExternalAPIStat(statisticsDao.getExternalAPIStat(request.getStrStartDate(), request.getStrEndDate()));
        res.setEnterpriseSyncNum(statisticsDao.getEnterpiseSycNum(request.getStrStartDate(), request.getStrEndDate()));
        return res;
    }

    public CustomerStatResponse getCustomerStatInfo(CustomerStatRequest request) throws Exception {
        LogUtil.info(this.getClass(), "Execute getCustomerStatInfo service");
        CustomerStatResponse res = new CustomerStatResponse();
        res.setCustomerLoginTime(statisticsDao.countCustomerLogin(request.getStrStartDate(), request.getStrEndDate()));
        res.setCustomerRegisterTime(statisticsDao.countCustomerRegister(request.getStrStartDate(), request.getStrEndDate()));
        res.setCustormerStat(statisticsDao.getCustomerStat(request.getCustomerTopN()));
        res.setCustomerDistri(statisticsDao.getCustomerDistribution());
        return res;
    }

    public EnterpriseStatResponse getEnterpriseStatInfo(EnterpriseStatRequest request) throws Exception {
        LogUtil.info(this.getClass(), "Execute getEnterpriseStatInfo service");
        EnterpriseStatResponse res = new EnterpriseStatResponse();
        res.setEnterpriseStat(statisticsDao.getEnterpriseStat(request.getTopN()));
        res.setEnterpriseDistri(statisticsDao.getEnterpriseDistribution());
        return res;
    }
}
