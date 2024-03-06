package com.first.lab.MainBanks.Amount;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an amount of currency.
 */
@Getter
@NoArgsConstructor
public class Amount implements IAmount {
    private double value;

    /**
     * Constructs a new Amount object with the given value.
     * @param value The value of the amount.
     */
    public Amount(double value) {
        this.value = validateValue(value);
    }

    /**
     * Sets the value of the amount.
     * @param value The value to set.
     */
    @Override
    public void setValue(double value) {
        this.value = validateValue(value);
    }

    /**
     * Gets the value of the amount.
     * @param value The value to get.
     * @return The value of the amount.
     */
    @Override
    public double getValue(double value) {
        return value;
    }

    /**
     * Validates and returns the value of the amount.
     * @param value The value to validate.
     * @return The validated value of the amount.
     * @throws IllegalStateException if the value is negative.
     */
    public static double validateValue(double value) {
        if (value < 0)
            throw new IllegalStateException("Invalid value amount: " + value);
        return value;
    }
}
