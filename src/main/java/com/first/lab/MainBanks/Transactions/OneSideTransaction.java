package com.first.lab.MainBanks.Transactions;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Contract.IAccount;
import com.first.lab.MainBanks.Contract.ITransaction;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class OneSideTransaction implements ITransaction {
    public OneSideTransaction(IAccount actor, Amount value)
    {
        this.actor = actor;
        this.value = value;
        this.id = UUID.randomUUID();
    }

    public UUID id;
    public IAccount actor;
    public Amount value;
}
