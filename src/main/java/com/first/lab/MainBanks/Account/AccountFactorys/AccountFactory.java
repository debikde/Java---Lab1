package com.first.lab.MainBanks.Account.AccountFactorys;

import com.first.lab.MainBanks.Contract.IAccount;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AccountFactory {
    public abstract IAccount createAccount(Class<? extends IAccount> accountClass);
}