package com.first.lab.MainBanks.Account;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Banks.BankInfo.BankInfo;
import com.first.lab.MainBanks.Contract.IAccount;
import com.first.lab.MainBanks.Contract.ITransaction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
public class DebitAccount implements IAccount {
    public UUID id;
    private final List<ITransaction> transactions;
    private final Amount moneyBank;
    private final Amount commission;
    private final BankInfo bankInfo;
    private Boolean isVerified;
    public DebitAccount(Amount balance, BankInfo bankInfo, Boolean verifiedStatus){
        this.id = UUID.randomUUID();
        this.transactions = new ArrayList<>();
        this.moneyBank = balance;
        this.bankInfo = bankInfo;
        this.commission = new Amount();
        this.isVerified = verifiedStatus;
    }

    @Override
    public void increaseMoneyValue(Amount value) {
        moneyBank.setValue(moneyBank.getValue() + value.getValue());
    }

    @Override
    public void remitTo(IAccount account, Amount value) {
        decreaseMoneyValue(value);
        account.increaseMoneyValue(value);
    }

    @Override
    public int getCommissionDay() {
       return bankInfo.getCommissionDay();
    }

    @Override
    public void removeWithdrawLimit() {
        isVerified = true;
    }

    @Override
    public void evaluateCommission() {
        commission.setValue(commission.getValue() + (moneyBank.getValue() * bankInfo.getDebitPercent().getValue()));
       }

    @Override
    public void accrueCommission() {
        increaseMoneyValue(commission);
        commission.setValue(0);
    }

    @Override
    public void addTransaction(ITransaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<ITransaction> getTransactions() {
        return List.copyOf(transactions);
    }
    @Override
    public void decreaseMoneyValue(Amount value) {
        moneyBank.setValue(moneyBank.getValue() - validateValue(value.getValue()));
    }
    private double validateValue(double value) {
        return !isVerified ? Math.min(bankInfo.getUntrustedUserWithdrawLimit().getValue(), value) : value;
    }

}
