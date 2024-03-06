package com.first.lab.MainBanks.Banks.TimeMachine;

import com.first.lab.MainBanks.Banks.Bank;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
public class TimeMachine {
    private static final TimeMachine INSTANCE = new TimeMachine(UUID.randomUUID());

    private final List<Bank> banks;
    private final UUID id;
    private LocalDate currentDate;
    private TimeMachine(UUID id) {
        this.id = id;
        this.banks = new ArrayList<>();
        this.currentDate = LocalDate.now();
    }

    public static TimeMachine getInstance() {
        return INSTANCE;
    }

    public void skipDays(int amount) {
        for (int i = 0; i < amount; i++) {
            skipDay();
        }
    }

    public void addObserverBank(Bank bank) {
        banks.add(bank);
    }

    public void skipDay() {
        currentDate = currentDate.plusDays(1);
        for (Bank bank : banks) {
            bank.notify(currentDate);
        }
    }


}
