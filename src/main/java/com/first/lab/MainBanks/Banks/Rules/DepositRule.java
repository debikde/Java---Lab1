package com.first.lab.MainBanks.Banks.Rules;

import com.first.lab.MainBanks.Amount.Amount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositRule {
    public Amount criticalValue;
    public Amount percent;
    public DepositRule(double criticalValue, double percent) {
        this.criticalValue = new Amount(criticalValue);
        this.percent = new Amount(percent);
    }
    public boolean isOverlapping(DepositRule otherRule) {
        return criticalValue.equals(otherRule.getCriticalValue());
    }

    public DepositRule getMaximumPercent(DepositRule otherRule) {
        return percent.getValue() > otherRule.getPercent().getValue() ? this : otherRule;
    }
}
