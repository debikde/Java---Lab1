package com.first.lab.MainBanks.Transactions;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Contract.IAccount;

public class WithdrawTransaction extends OneSideTransaction{
    public WithdrawTransaction(IAccount actor, Amount value) {
        super(actor, value);
        actor.decreaseMoneyValue(value);
    }
}
