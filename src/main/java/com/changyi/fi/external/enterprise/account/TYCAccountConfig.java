package com.changyi.fi.external.enterprise.account;

import com.changyi.fi.core.config.ConfigDic;
import com.changyi.fi.core.config.ConfigManager;
import com.changyi.fi.vo.AccountPair;

import java.util.List;

public class TYCAccountConfig implements IAccountConfig {

    public int getMaxTime() {
        return ConfigManager.getIntegerParameter(ConfigDic.TYC_ACCOUNT_USE_TIME, 10);
    }

    public List<AccountPair> getAccountList() {
        return ConfigManager.getTYCAccountList();
    }

}
