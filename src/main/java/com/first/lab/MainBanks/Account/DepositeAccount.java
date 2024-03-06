package com.first.lab.MainBanks.Account;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Banks.BankInfo.BankInfo;
import com.first.lab.MainBanks.Banks.Rules.DepositRule;
import com.first.lab.MainBanks.Contract.IAccount;
import com.first.lab.MainBanks.Contract.ITransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DepositeAccount implements IAccount {
    public UUID id;
    private  List<ITransaction> transactions;
    private Amount moneyBank;
    private Amount commission;
    private BankInfo bankInfo;
    private Boolean isVerified;

    public DepositeAccount (Amount balance, BankInfo bankInfo, Boolean verifiedStatus){
        this.id = UUID.randomUUID();
        this.transactions = new ArrayList<>();
        this.moneyBank = balance;
        this.bankInfo = bankInfo;
        this.commission = new Amount();
        this.isVerified = verifiedStatus;
    }

    @Override
    public void increaseMoneyValue(Amount value) {
        this.moneyBank.setValue(this.moneyBank.getValue() + value.getValue());
    }

    @Override
    public void decreaseMoneyValue(Amount value) {
        this.moneyBank.setValue(this.moneyBank.getValue() - validateValue(value.getValue()));
    }

    @Override
    public void remitTo(IAccount account, Amount value) {
        decreaseMoneyValue(value);
        account.increaseMoneyValue(value);
    }

    @Override
    public int getCommissionDay() {
        return this.bankInfo.getCommissionDay();
    }

    @Override
    public void removeWithdrawLimit() {
        this.isVerified = true;
    }

    @Override
    public void evaluateCommission() {
        this.commission.setValue(this.commission.getValue() + this.moneyBank.getValue() * getDepositPercent().getValue());
    }

    @Override
    public void accrueCommission() {
        increaseMoneyValue(this.commission);
        this.commission.setValue(0);
    }

    @Override
    public void addTransaction(ITransaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<ITransaction> getTransactions() {
        return this.transactions;
    }
    private double validateValue(double value) {
        if (!this.isVerified) {
            return Math.min(this.bankInfo.getUntrustedUserWithdrawLimit().getValue(), value);
        }
        return value;
    }
    private Amount getDepositPercent() {
        Amount percent = new Amount(this.bankInfo.getDepositRules().getMaximumPercent().getValue());
        for (DepositRule depositRule : this.bankInfo.getDepositRules().getDepositRules()) {
            if (this.moneyBank.getValue() <= depositRule.getCriticalValue().getValue()) {
                percent = depositRule.getPercent();
            }
        }
        return percent;
    }
}
