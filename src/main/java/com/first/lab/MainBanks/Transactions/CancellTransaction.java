package com.first.lab.MainBanks.Transactions;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Contract.IAccount;

public class CancellTransaction extends TwoSideTransaction {
    public CancellTransaction(IAccount actor, IAccount recipient, Amount value) {
        super(actor, recipient, value);
        actor.remitTo(recipient, value);
    }
}
