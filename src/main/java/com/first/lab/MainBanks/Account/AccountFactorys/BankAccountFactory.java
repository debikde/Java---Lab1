package com.first.lab.MainBanks.Account.AccountFactorys;

import com.first.lab.MainBanks.Account.CreditAccount;
import com.first.lab.MainBanks.Account.DebitAccount;
import com.first.lab.MainBanks.Account.DepositeAccount;
import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Amount.CreditAmount;
import com.first.lab.MainBanks.Banks.BankInfo.BankInfo;
import com.first.lab.MainBanks.Contract.IAccount;

import java.util.HashMap;
import java.util.Map;

public class BankAccountFactory extends AccountFactory{
    private final BankInfo bankInfo;
    private final Long balance;
    private final boolean isVerified;
    private final Map<Class<? extends IAccount>, IAccountCreator> accountCreators;

    public BankAccountFactory(Long balance, BankInfo bankInfo, boolean isVerified) {
        this.balance = balance;
        this.bankInfo = bankInfo;
        this.isVerified = isVerified;
        accountCreators = new HashMap<>();
        accountCreators.put(CreditAccount.class, this::createCreditAccount);
        accountCreators.put(DebitAccount.class, this::createDebitAccount);
        accountCreators.put(DepositeAccount.class, this::createDepositAccount);
    }

    @Override
    public IAccount createAccount(Class<? extends IAccount> accountClass) {
        IAccountCreator creator = accountCreators.get(accountClass);
        if (creator == null) {
            throw new IllegalStateException("Invalid Account Type");
        }
        return creator.create();
    }
    private interface IAccountCreator {
        IAccount create();
    }
    private IAccount createDepositAccount() {
        return new DepositeAccount(new Amount(balance), bankInfo, isVerified);
    }

    private IAccount createDebitAccount() {
        return new DebitAccount(new Amount(balance), bankInfo, isVerified);
    }

    private IAccount createCreditAccount() {
        return new CreditAccount(new CreditAmount(balance), bankInfo, isVerified);
    }

}
