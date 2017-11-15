package com.changyi.fi.external.enterprise.account;

import com.changyi.fi.vo.AccountPair;

import java.util.List;

public class AccountPicker {

    private int currentIndex = 0;

    private int times = 0;

    public synchronized AccountPair nextAccount(boolean required, IAccountConfig config) {
        int maxTimes = config.getMaxTime();
        List<AccountPair> accountList = config.getAccountList();
        if (required) {
            this.times = 0;
            return accountList.get(this.currentIndex);
        }
        this.times++;
        if (this.times == maxTimes) {
            currentIndex++;
            if (currentIndex == accountList.size()) {
                currentIndex = 0;
            }
            this.times = 0;
            return accountList.get(this.currentIndex);
        } else {
            return null;
        }
    }
}
