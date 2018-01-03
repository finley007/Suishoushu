package com.changyi.fi.component.statistics.service;

import com.changyi.fi.component.statistics.request.CustomerStatRequest;
import com.changyi.fi.component.statistics.request.EnterpriseStatRequest;
import com.changyi.fi.component.statistics.request.MerchantStatRequest;
import com.changyi.fi.component.statistics.request.SystemStatRequest;
import com.changyi.fi.component.statistics.response.CustomerStatResponse;
import com.changyi.fi.component.statistics.response.EnterpriseStatResponse;
import com.changyi.fi.component.statistics.response.MerchantStatResponse;
import com.changyi.fi.component.statistics.response.SystemStatResponse;

public interface StatisticsService {

    public CustomerStatResponse getCustomerStatInfo(CustomerStatRequest request) throws Exception;

    public EnterpriseStatResponse getEnterpriseStatInfo(EnterpriseStatRequest request) throws Exception;

    public SystemStatResponse getSystemStatInfo(SystemStatRequest request) throws Exception;

    public MerchantStatResponse getMerchantStatInfo(MerchantStatRequest request) throws Exception;
}
