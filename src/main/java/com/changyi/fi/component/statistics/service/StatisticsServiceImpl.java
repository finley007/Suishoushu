package com.changyi.fi.component.statistics.service;

import java.util.Date;
import java.util.Calendar;

import com.changyi.fi.component.statistics.request.CustomerStatRequest;
import com.changyi.fi.component.statistics.request.EnterpriseStatRequest;
import com.changyi.fi.component.statistics.request.MerchantStatRequest;
import com.changyi.fi.component.statistics.request.SystemStatRequest;
import com.changyi.fi.component.statistics.response.CustomerStatResponse;
import com.changyi.fi.component.statistics.response.EnterpriseStatResponse;
import com.changyi.fi.component.statistics.response.MerchantStatResponse;
import com.changyi.fi.component.statistics.response.SystemStatResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.dao.StatisticsDao;
import com.changyi.fi.util.FIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    private static final int INACTIVE_LIMIT = -10;

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
        res.setCustomerLoginCount(statisticsDao.countCustomerLogin(request.getStrStartDate(), request.getStrEndDate()));
        res.setCustomerRegisterCount(statisticsDao.countCustomerRegister(request.getStrStartDate(), request.getStrEndDate()));
        res.setCustormerStat(statisticsDao.getCustomerStat(request.getCustomerTopN()));
        res.setCustomerDistri(statisticsDao.getCustomerDistribution());
        res.setTotalCustomerCount(statisticsDao.countTotalCustomer());
        res.setInactiveCustomerCount(statisticsDao.countInactiveCustomer(getInactiveLimit()));
        return res;
    }

    public EnterpriseStatResponse getEnterpriseStatInfo(EnterpriseStatRequest request) throws Exception {
        LogUtil.info(this.getClass(), "Execute getEnterpriseStatInfo service");
        EnterpriseStatResponse res = new EnterpriseStatResponse();
        res.setEnterpriseStat(statisticsDao.getEnterpriseStat(request.getTopN()));
        res.setEnterpriseDistri(statisticsDao.getEnterpriseDistribution());
        return res;
    }

    private String getInactiveLimit() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, INACTIVE_LIMIT);
        return FIConstants.sdf.format(cal.getTime());
    }

    public MerchantStatResponse getMerchantStatInfo(MerchantStatRequest request) throws Exception {
        LogUtil.info(this.getClass(), "Execute getMerchantStatInfo service");
        MerchantStatResponse res = new MerchantStatResponse();
        res.setMerchantStat(statisticsDao.getMerchantStat(request.getStrStartDate(), request.getStrEndDate()));
        return res;
    }
}
