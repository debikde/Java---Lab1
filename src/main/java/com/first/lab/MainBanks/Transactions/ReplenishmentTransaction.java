package com.first.lab.MainBanks.Transactions;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Contract.IAccount;

public class ReplenishmentTransaction extends OneSideTransaction{
    public ReplenishmentTransaction(IAccount actor, Amount value) {
        super(actor, value);
        actor.increaseMoneyValue(value);
    }
}
