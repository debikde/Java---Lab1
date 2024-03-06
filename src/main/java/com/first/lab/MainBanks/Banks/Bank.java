package com.first.lab.MainBanks.Banks;

import com.first.lab.MainBanks.Account.AccountFactorys.BankAccountFactory;
import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Banks.BankInfo.BankInfo;
import com.first.lab.MainBanks.Contract.IAccount;
import com.first.lab.MainBanks.Contract.IBank;
import com.first.lab.MainBanks.Contract.IClient;
import com.first.lab.MainBanks.Contract.IObserverObject;
import com.first.lab.MainBanks.Transactions.CancellTransaction;
import com.first.lab.MainBanks.Transactions.ReplenishmentTransaction;
import com.first.lab.MainBanks.Transactions.TransferTransaction;
import com.first.lab.MainBanks.Transactions.WithdrawTransaction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Represents a bank entity that manages client accounts and transactions.
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Bank implements IBank {

    private final List<IClient> clients;
    private final List<IObserverObject> observerClients;
    private final List<IAccount> accounts;
    private final String name;
    private final UUID id;
    private BankInfo bankInfo;
    /**
     * Constructs a new Bank with the given name and bank information.
     * @param name The name of the bank.
     * @param bankInfo The bank information for the bank.
     */
    public Bank(String name, BankInfo bankInfo) {
        this.name = name;
        this.bankInfo = bankInfo;
        this.id = UUID.randomUUID();
        this.clients = new ArrayList<>();
        this.observerClients = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds a client to the bank.
     * @param client The client to add.
     */
    public void addClient (IClient client){
        clients.add(client);
    }

    /**
     * Removes a client from the bank.
     * @param client The client to remove.
     */
    public void removeClient (IClient client){
        clients.remove(client);
    }

    /**
     * Creates a new account for the given client with the specified balance.
     * @param client The client for whom to create the account.
     * @param balance The initial balance for the account.
     * @return The UUID of the newly created account.
     */
    public UUID createAccount(IClient client, double balance) {
        BankAccountFactory accountFactory = new BankAccountFactory((long) balance, bankInfo, client.isVerified());
        IAccount account = accountFactory.createAccount(IAccount.class);
        accounts.add(account);
        if (!clients.contains(client)) {
            clients.add(client);
        }
        client.AddAccount(account);
        return account.getId();
    }

    public void createTransaction(UUID actorId, UUID recipientId, double value) {
        IAccount actorAccount = getActor(actorId);
        IAccount recipientAccount = getActor(recipientId);
        TransferTransaction transaction = new TransferTransaction(actorAccount, recipientAccount, new Amount(value));
        actorAccount.addTransaction(transaction);
        recipientAccount.addTransaction(transaction);
    }

    public void createTransaction(UUID actorId, IAccount recipientAccount, double value) {
        IAccount actorAccount = getActor(actorId);
        TransferTransaction transaction = new TransferTransaction(actorAccount, recipientAccount, new Amount(value));
        actorAccount.addTransaction(transaction);
        recipientAccount.addTransaction(transaction);
    }

    public void cancelTransaction(UUID actorId, UUID recipientId, double value) {
        IAccount actorAccount = getActor(actorId);
        IAccount recipientAccount = getActor(recipientId);
        CancellTransaction transaction = new CancellTransaction(actorAccount, recipientAccount, new Amount(value));
        actorAccount.addTransaction(transaction);
        recipientAccount.addTransaction(transaction);
    }

    public void cancelTransaction(UUID actorId, IAccount recipientAccount, double value) {
        IAccount actorAccount = getActor(actorId);
        CancellTransaction transaction = new CancellTransaction(actorAccount, recipientAccount, new Amount(value));
        actorAccount.addTransaction(transaction);
        recipientAccount.addTransaction(transaction);
    }

    public void createWithdrawTransaction(UUID actorId, double value) {
        IAccount actorAccount = getActor(actorId);
        WithdrawTransaction transaction = new WithdrawTransaction(actorAccount, new Amount(value));
        actorAccount.addTransaction(transaction);
    }

    public void createReplenishmentTransaction(UUID actorId, double value) {
        IAccount actorAccount = getActor(actorId);
        ReplenishmentTransaction transaction = new ReplenishmentTransaction(actorAccount, new Amount(value));
        actorAccount.addTransaction(transaction);
    }

    public void notify(LocalDate currentDate) {
        for (IAccount account : accounts) {
            account.evaluateCommission();
            if (currentDate.getDayOfMonth() == validateCommissionDay(account.getCommissionDay(), currentDate)) {
                account.accrueCommission();
            }
        }
    }

    @Override
    public void addObserverObject(IObserverObject observer) {
        if (observerClients.contains(observer)) {
            throw new IllegalStateException("Observer already exists in the list.");
        }
        observerClients.add(observer);
    }

    @Override
    public void removeObserverObject(IObserverObject observer) {
        if (!observerClients.contains(observer)) {
            throw new IllegalStateException("Observer not exists in the list.");
        }
        observerClients.remove(observer);
    }

    @Override
    public UUID createAccount(IClient client, BigDecimal balance) {
        return null;
    }

    @Override
    public void processTransactions() {

    }

    /**
     * Updates the bank's bank information and notifies clients.
     * @param bankInfo The new bank information.
     */
    public void UpdateBankInfo(BankInfo bankInfo)
    {
        this.bankInfo = bankInfo;
        notifyClients();
    }

    /**
     * Gets the account associated with the given ID.
     * @param id The ID of the account to retrieve.
     * @return The account associated with the given ID.
     * @throws IllegalStateException if no account is found with the given ID.
     */
    public IAccount getActor(UUID id) {
        for (IAccount account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        throw new IllegalStateException("Invalid account ID: " + id);
    }

    /**
     * Notifies observer clients about the bank's information update.
     */
    private void notifyClients() {
        for (IObserverObject client : observerClients) {
            client.update(bankInfo.toString());
        }
    }

    /**
     * Validates and returns the commission day based on the current date.
     * @param commissionDay The commission day configured for the bank.
     * @param currentDate The current date.
     * @return The validated commission day.
     * @throws IllegalStateException if the commission day is invalid.
     */
    private int validateCommissionDay(int commissionDay, LocalDate currentDate) {
        int daysInMonth = currentDate.lengthOfMonth();
        if (commissionDay > daysInMonth) {
            throw new IllegalStateException("Invalid commission day: " + commissionDay);
        }
        return commissionDay;
    }
}
