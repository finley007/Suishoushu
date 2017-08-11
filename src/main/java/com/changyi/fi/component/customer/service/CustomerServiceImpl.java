package com.changyi.fi.component.customer.service;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.dao.CustomerDao;
import com.changyi.fi.model.CustomerLoginPO;
import com.changyi.fi.model.CustomerPO;
import com.changyi.fi.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    @Autowired(required = true)
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Validate
    public void updateCustomer(Customer info, String openId) throws Exception {
        LogUtil.info(this.getClass(), "Update customer info in DB");
        CustomerPO po = new CustomerPO();
        po.setCity(info.getCity());
        po.setCountry(info.getCountry());
        po.setGender(Short.valueOf(info.getGendar()));
        po.setNickName(info.getNickName());
        po.setLastLoginTime(new Date());
        po.setProvince(info.getProvince());
        po.setOpenId(openId);
        if (customerDao.countCustomerById(openId) > 0) {
            customerDao.updateSelective(po);
        } else {
            po.setCreateTime(new Date());
            customerDao.insert(po);
        }

        CustomerLoginPO loginPO = new CustomerLoginPO();
        loginPO.setOpenId(openId);
        loginPO.setLoginTime(new Date());
        customerDao.insertCustomerLogin(loginPO);
    }
}
