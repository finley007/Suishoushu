package com.changyi.fi.component.customer.service;

import com.changyi.fi.vo.Customer;

public interface CustomerService {

    public void updateCustomer(Customer customer, String openId) throws Exception;
}
