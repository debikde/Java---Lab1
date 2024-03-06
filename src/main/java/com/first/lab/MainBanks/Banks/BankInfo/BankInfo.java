package com.first.lab.MainBanks.Banks.BankInfo;

import com.first.lab.MainBanks.Amount.Amount;
import com.first.lab.MainBanks.Banks.Rules.DepositRules;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankInfo {
    private final int commissionDay;
    private final Amount freezeTime;
    private final Amount debitPercent;
    private final Amount creditLimit;
    private final Amount creditCommission;
    private final Amount untrustedUserWithdrawLimit;
    private final DepositRules depositRules;

    /**
     * Constructs a new BankInfo object with the given parameters.
     * @param commissionDay The day of the month on which the commission is calculated.
     * @param freezeTime The freeze time for deposited funds.
     * @param debitPercent The percentage for debit transactions.
     * @param untrustedUserWithdrawLimit The withdrawal limit for untrusted users.
     * @param creditLimit The credit limit for credit accounts.
     * @param creditCommission The commission rate for credit accounts.
     * @param depositRules The deposit rules for the bank.
     */
    public BankInfo(int commissionDay, long freezeTime, double debitPercent,
                    double untrustedUserWithdrawLimit, double creditLimit,
                    double creditCommission, DepositRules depositRules)
    {
        this.commissionDay = validateCommissionPeriod(commissionDay);
        this.freezeTime = new Amount(freezeTime);
        this.debitPercent = new Amount(debitPercent);
        this.untrustedUserWithdrawLimit = new Amount(untrustedUserWithdrawLimit);
        this.creditLimit = new Amount(creditLimit);
        this.creditCommission = new Amount(creditCommission);
        this.depositRules = depositRules;
    }

    /**
     * Validates and returns the commission period.
     * @param value The commission period to validate.
     * @return The validated commission period.
     * @throws IllegalArgumentException if the commission period is not a positive integer.
     */
    private static int validateCommissionPeriod(int value) {
        if (value > 0)
            return value;
        throw new IllegalArgumentException("Commission period must be a positive integer.");
    }
}
