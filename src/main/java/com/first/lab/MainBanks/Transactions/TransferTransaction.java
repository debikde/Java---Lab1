package com.first.lab.MainBanks.Transactions;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Contract.IAccount;

public class TransferTransaction extends TwoSideTransaction {
    public TransferTransaction(IAccount actor, IAccount recipient, Amount value) {
        super(actor, recipient, value);
        actor.remitTo(recipient, value);
    }
}
