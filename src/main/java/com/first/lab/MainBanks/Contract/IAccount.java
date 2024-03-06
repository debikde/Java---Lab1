package com.first.lab.MainBanks.Contract;

import com.first.lab.MainBanks.Amount.Amount;

import java.util.List;
import java.util.UUID;

public interface IAccount {
    UUID getId();
    void increaseMoneyValue(Amount value);
    void decreaseMoneyValue(Amount value);
    void remitTo(IAccount account, Amount value);
    int getCommissionDay();
    void removeWithdrawLimit();
    void evaluateCommission();
    void accrueCommission();
    void addTransaction(ITransaction transaction);
    List<ITransaction> getTransactions();
}
