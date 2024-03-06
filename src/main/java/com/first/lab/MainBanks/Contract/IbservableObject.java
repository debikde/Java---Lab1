package com.first.lab.MainBanks.Contract;

public interface IbservableObject {
    void addObserverObject(IObserverObject observer);
    void removeObserverObject(IObserverObject observer);
}
