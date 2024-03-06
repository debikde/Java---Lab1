package com.first.lab.MainBanks.Amount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreditAmount implements IAmount{
    private double value;
    @Override
    public void setValue(double value) {
        this.value = value;
    }
    @Override
    public double getValue(double value) {
        return value;
    }
}
