package com.changyi.fi.external.enterprise.account;

import com.changyi.fi.vo.AccountPair;

import java.util.List;

public interface IAccountConfig {

    public int getMaxTime();

    public List<AccountPair> getAccountList();
}
