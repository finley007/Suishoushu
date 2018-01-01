package com.changyi.fi.component.customer.service;

import com.changyi.fi.vo.Channel;
import com.changyi.fi.vo.Customer;

import java.util.List;

public interface CustomerService {

    public void updateCustomer(Customer customer, String openId, boolean isLogin) throws Exception;

    public String updateChannel(Channel channel) throws Exception;

    public List<Channel> listChannel() throws Exception;
}
