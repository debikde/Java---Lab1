package com.first.lab.MainBanks.Transactions;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Contract.IAccount;
import com.first.lab.MainBanks.Contract.ITransaction;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public abstract class TwoSideTransaction implements ITransaction {
    protected TwoSideTransaction(IAccount actor, IAccount recipient, Amount value)
    {
        this.actor = actor;
        this.recipient = recipient;
        this.value = value;
        this.id = UUID.randomUUID();
    }

    public UUID id;
    public IAccount actor;
    public IAccount recipient;
    public Amount value;
}
