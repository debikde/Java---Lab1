package com.first.lab.MainBanks.Banks.BankInfo;

import com.first.lab.MainBanks.Banks.Rules.DepositRules;

public class BankInfoBuilder {
    private int commissionDay;
    private long freezeTime;
    private double debitPercent;
    private double creditLimit;
    private double creditCommission;
    private double untrustedUserWithdrawLimit;
    private DepositRules depositRules;

    public BankInfoBuilder setCommissionDay(int commissionDay) {
        this.commissionDay = commissionDay;
        return this;
    }

    public BankInfoBuilder setFreezeTime(long freezeTime) {
        this.freezeTime = freezeTime;
        return this;
    }

    public BankInfoBuilder setDebitPercent(double debitPercent) {
        this.debitPercent = debitPercent;
        return this;
    }

    public BankInfoBuilder setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    public BankInfoBuilder setCreditCommission(double creditCommission) {
        this.creditCommission = creditCommission;
        return this;
    }

    public BankInfoBuilder setUntrustedUserWithdrawLimit(double untrustedUserWithdrawLimit) {
        this.untrustedUserWithdrawLimit = untrustedUserWithdrawLimit;
        return this;
    }

    public BankInfoBuilder setDepositRules(DepositRules depositRules) {
        this.depositRules = depositRules;
        return this;
    }

    public BankInfo build() {
        return new BankInfo(commissionDay, freezeTime, debitPercent,
                untrustedUserWithdrawLimit, creditLimit, creditCommission, depositRules);
    }
}