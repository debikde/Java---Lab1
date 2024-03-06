package com.first.lab.MainBanks.Account;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Amount.CreditAmount;
import com.first.lab.MainBanks.Banks.BankInfo.BankInfo;
import com.first.lab.MainBanks.Contract.IAccount;
import com.first.lab.MainBanks.Contract.ITransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Credit Account for a bank.
 */

@NoArgsConstructor
@Getter
@Setter
public class CreditAccount implements IAccount {
    public UUID id;
    private List<ITransaction> transactions;
    public CreditAmount moneyBank;
    private Amount commission;
    private BankInfo bankInfo;
    private Boolean isVerified;

    /**
     * Constructs a new Credit Account with the given balance, bank info, and verification status.
     * @param balance The initial balance of the credit account.
     * @param bankInfo The bank information associated with the credit account.
     * @param verifiedStatus The verification status of the credit account.
     */
    public CreditAccount(CreditAmount balance, BankInfo bankInfo, Boolean verifiedStatus)
    {
        this.id = UUID.randomUUID();
        this.transactions = new ArrayList<>();
        this.moneyBank = balance;
        this.bankInfo = bankInfo;
        this.commission = new Amount();
        this.isVerified = verifiedStatus;
    }

    /**
     * Constructs a new Credit Account with the given list of transactions.
     * @param transactions The list of transactions associated with the credit account.
     */
    public CreditAccount(List<ITransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Increases the money value in the credit account by the given amount.
     * @param value The amount to increase the money value by.
     */
    @Override
    public void increaseMoneyValue(Amount value) {
        moneyBank.setValue(moneyBank.getValue() + value.getValue());
    }

    /**
     * Decreases the money value in the credit account by the given amount.
     * @param value The amount to decrease the money value by.
     */
    @Override
    public void decreaseMoneyValue(Amount value) {
        moneyBank.setValue(moneyBank.getValue() - value.getValue());
    }

    /**
     * Transfers money from this credit account to another account.
     * @param account The account to transfer money to.
     * @param value The amount of money to transfer.
     */
    @Override
    public void remitTo(IAccount account, Amount value) {
        decreaseMoneyValue(value);
        account.increaseMoneyValue(value);

    }
    @Override
    public int getCommissionDay() {
        return bankInfo.getCommissionDay();
    }

    /**
     * Removes the withdraw limit for the credit account.
     */
    @Override
    public void removeWithdrawLimit() {
        isVerified = true;
    }

    /**
     * Evaluates the commission for the credit account.
     */
    @Override
    public void evaluateCommission() {
        commission.setValue(commission.getValue() + validateCreditValue());
    }

    /**
     * Accrues the commission for the credit account.
     */
    @Override
    public void accrueCommission() {
        decreaseMoneyValue(commission);
        commission.setValue(0);
    }

    /**
     * Adds a transaction to the list of transactions associated with the credit account.
     * @param transaction The transaction to add.
     */
    @Override
    public void addTransaction(ITransaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<ITransaction> getTransactions() {
        return transactions;
    }

    /**
     * Validates and returns the value based on the account's verification status.
     * @param value The value to validate.
     * @return The validated value.
     */
    private double validateValue(double value) {
        if (!isVerified) {
            return Math.min(bankInfo.getUntrustedUserWithdrawLimit().getValue(), value);
        } else {
            return value;
        }
    }

    /**
     * Validates and returns the credit commission based on the credit account's balance.
     * @return The credit commission value.
     */
    private double validateCreditValue() {
        return (moneyBank.getValue() < 0) ? bankInfo.getCreditCommission().getValue() : 0;
    }
}
