package com.first.lab.MainBanks.Clients;

import com.first.lab.MainBanks.Contract.IAccount;
import com.first.lab.MainBanks.Contract.IBank;
import com.first.lab.MainBanks.Contract.IClient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a client of a bank.
 */
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Client implements IClient
{
    Boolean IsVerified;
    UUID Id;
    String Name;
    String LastName;
    String Address;
    String Passport;
    private final List<String> clientHistory = new ArrayList<>();
    private final List<IAccount> accounts = new ArrayList<>();
    private final List<IAccount> accountsObservable = new ArrayList<>();

    /**
     * Constructs a new Client object with the specified parameters.
     * @param id The unique ID of the client.
     * @param name The first name of the client.
     * @param lastName The last name of the client.
     * @param address The address of the client.
     * @param passport The passport number of the client.
     */
    public Client(UUID id, String name, String lastName, String address, String passport) {
        Id = id;
        Name = name;
        LastName = lastName;
        Address = address;
        Passport = passport;
    }

    /**
     * Subscribes the client to receive updates from a bank.
     * @param bank The bank to subscribe to.
     */
    public void subscribeToBank(IBank bank) {
        bank.addObserverObject(this);
    }

    /**
     * Unsubscribes the client from receiving updates from a bank.
     * @param bank The bank to unsubscribe from.
     */
    public void unsubscribeFromBank(IBank bank) {
        bank.removeObserverObject(this);
    }

    /**
     * Notifies accounts that are observable to remove the withdraw limit.
     */
    private void notifyAccounts()
    {
        if (accountsObservable.isEmpty()) return;
        for (IAccount account : accountsObservable) {
            account.removeWithdrawLimit();
        }
    }

    /**
     * Checks if the client is verified.
     * @return true if the client is verified, false otherwise.
     */
    @Override
    public boolean isVerified() {
        return !Address.isEmpty() && !Passport.isEmpty();
    }

    @Override
    public void update(String logMessage) {
        clientHistory.add(logMessage);
    }

    @Override
    public void AddAccount(IAccount account) {
        if (!isVerified())
            accountsObservable.add(account);
        accounts.add(account);
    }
}
