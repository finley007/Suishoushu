package com.changyi.fi.auth;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.dao.CustomerDao;
import com.changyi.fi.external.weixin.WeixinAPIService;
import com.changyi.fi.model.CustomerPO;
import com.changyi.fi.vo.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by finley on 1/25/17.
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Resource
    private WeixinAPIService weixinAPIService;

    private CustomerDao customerDao;

    @Autowired(required = true)
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public AuthResponse authenticate(String code) throws Exception {
        LogUtil.info(this.getClass(), "Do authentication");
        //调用微信服务获取当前登录用户信息
        CustomerInfo customerInfo = weixinAPIService.login(code);
        //更新数据库用户信息
        this.saveOrUpdateCustomerInfo(customerInfo);
        //根据openid生成session并保存在token中
        return new AuthResponse(new Token(customerInfo).getKey());
    }

    private void saveOrUpdateCustomerInfo(CustomerInfo info) {
        LogUtil.info(this.getClass(), "Update customer info in DB");
        CustomerPO po = new CustomerPO();
        po.setCity(info.getCity());
        po.setCountry(info.getCountry());
        po.setGender(Short.valueOf(info.getGendar()));
        po.setNickName(info.getNickName());
        po.setLastLoginTime(new Date());
        po.setProvince(info.getProvince());
        po.setOpenId(info.getOpenId());
        if (customerDao.countCustomerById(info.getOpenId()) > 0) {
            customerDao.updateSelective(po);
        } else {
            po.setCreateTime(new Date());
            customerDao.insert(po);
        }

    }


}
