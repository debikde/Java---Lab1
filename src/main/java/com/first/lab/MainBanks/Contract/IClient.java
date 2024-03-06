package com.first.lab.MainBanks.Contract;

public interface IClient extends IObserverObject {
    void AddAccount(IAccount account);
    boolean isVerified();
}
