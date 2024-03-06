package com.first.lab.MainBanks.Banks;

import com.first.lab.MainBanks.Banks.BankInfo.BankInfo;
import com.first.lab.MainBanks.Banks.TimeMachine.TimeMachine;
import com.first.lab.MainBanks.Contract.IAccount;
import com.first.lab.MainBanks.Contract.IBank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * CentralBank represents the central authority managing multiple banks.
 * It provides operations to create banks, manage transactions between banks, and retrieve bank accounts.
 */
@Getter
@Setter
@AllArgsConstructor
public class CentralBank {

    private static final CentralBank INSTANCE = new CentralBank(UUID.randomUUID(), TimeMachine.getInstance());
    private final List<IBank> banks;
    private final UUID id;
    private final TimeMachine timeMachine;
    private CentralBank(UUID id, TimeMachine timeMachine){
        this.id = id;
        this.timeMachine = timeMachine;
        this.banks = new ArrayList<>();
    }
    /**
     * The singleton instance of CentralBank.
     */
    /**
     * Get the singleton instance of CentralBank.
     * @return The singleton instance of CentralBank.
     */
    public static CentralBank getInstance() {
        return INSTANCE;
    }
    public Bank createBank(String bankName, BankInfo bankInfo) {
        Bank bank = new Bank(bankName, bankInfo);
        if (banks.contains(bank)) {
            throw new IllegalStateException("Bank already exists: " + bank);
        }
        timeMachine.addObserverBank(bank);
        banks.add(bank);
        return bank;
    }

    /**
     * Create a transaction between two accounts.
     * @param actorId The ID of the actor initiating the transaction.
     * @param recipientId The ID of the recipient account.
     * @param value The amount of money to transfer.
     */
    public void createTransaction(UUID actorId, UUID recipientId, double value) {
        Bank actorBank = (Bank) banks.stream()
                .filter(bank -> bank.getAccounts().stream().anyMatch(account -> account.getId().equals(actorId)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid bank for actor: " + actorId));
        Bank recipientBank = (Bank) banks.stream()
                .filter(bank -> bank.getAccounts().stream().anyMatch(account -> account.getId().equals(recipientId)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid bank for recipient: " + recipientId));

        if (actorBank.equals(recipientBank)) {
            actorBank.createTransaction(actorId, recipientId, value);
        } else {
            IAccount recipientAccount = getActor(recipientId);
            actorBank.createTransaction(actorId, recipientAccount, value);
        }
    }

    /**
     * Cancel a transaction between two accounts.
     * @param actorId The ID of the actor initiating the transaction.
     * @param recipientId The ID of the recipient account.
     * @param value The amount of money to cancel.
     */
    public void cancelTransaction(UUID actorId, UUID recipientId, double value) {
        Bank actorBank = (Bank) banks.stream()
                .filter(bank -> bank.getAccounts().stream().anyMatch(account -> account.getId().equals(actorId)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid bank for actor: " + actorId));
        Bank recipientBank = (Bank) banks.stream()
                .filter(bank -> bank.getAccounts().stream().anyMatch(account -> account.getId().equals(recipientId)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid bank for recipient: " + recipientId));

        if (actorBank.equals(recipientBank)) {
            actorBank.cancelTransaction(actorId, recipientId, value);
        } else {
            IAccount recipientAccount = getActor(recipientId);
            actorBank.cancelTransaction(actorId, recipientAccount, value);
        }
    }

    /**
     * Set the bank information for a specific bank.
     * @param bank The bank for which to set the bank information.
     * @param bankInfo The new bank information.
     */
    public void setBankInfo(Bank bank, BankInfo bankInfo)
    {
        bank.UpdateBankInfo(bankInfo);
    }

    /**
     * Get the account associated with the given ID.
     * @param id The ID of the account to retrieve.
     * @return The account associated with the given ID.
     * @throws IllegalStateException if no account is found with the given ID.
     */
    public IAccount getActor(UUID id) {
        return banks.stream()
                .flatMap(bank -> bank.getAccounts().stream())
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid account: " + id));
    }

}
