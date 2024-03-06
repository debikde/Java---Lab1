package com.first.lab.TestBank;

import com.first.lab.MainBanks.Banks.Bank;
import com.first.lab.MainBanks.Banks.BankInfo.BankInfo;
import com.first.lab.MainBanks.Banks.BankInfo.BankInfoBuilder;
import com.first.lab.MainBanks.Banks.CentralBank;
import com.first.lab.MainBanks.Banks.Rules.DepositRule;
import com.first.lab.MainBanks.Banks.Rules.DepositRules;
import com.first.lab.MainBanks.Banks.TimeMachine.TimeMachine;
import com.first.lab.MainBanks.Clients.Client;
import com.first.lab.MainBanks.Contract.IBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestsBank {
    private CentralBank centralBank;
    private BankInfo bankInfo;
    private TimeMachine timeMachine;
    private Bank bank1;
    private Bank bank2;

    @BeforeEach
    void setUp() {
        centralBank = CentralBank.getInstance();
        timeMachine = TimeMachine.getInstance();
        bankInfo = new BankInfoBuilder()
                .setCommissionDay(15)
                .setFreezeTime(24)
                .setDebitPercent(0.1)
                .setCreditLimit(10000)
                .setCreditCommission(0.05)
                .setUntrustedUserWithdrawLimit(500)
                .setDepositRules(createDepositRules())
                .build();
        bank1 = new Bank("Bank1", bankInfo);
        bank2 = new Bank("Bank2",bankInfo );
    }

    @Test
    void testCreateBank() {

        Bank bank = centralBank.createBank("Test Bank", bankInfo);

        Assertions.assertNotNull(bank);
        Assertions.assertEquals("Test Bank", bank.getName());
        Assertions.assertEquals(bankInfo, bank.getBankInfo());
    }
    private DepositRules createDepositRules() {
        List<DepositRule> depositRules = new ArrayList<>();
        depositRules.add(new DepositRule(100, 0.01));
        depositRules.add(new DepositRule(500, 0.03));
        depositRules.add(new DepositRule(1000, 0.05));

        return new DepositRules.DepositRulesBuilder()
                .addDepositRule(new DepositRule(100, 0.01))
                .addDepositRule(new DepositRule(500, 0.03))
                .addDepositRule(new DepositRule(1000, 0.05))
                .build();
    }
    @Test
    void testCreateAccountInBank() {
        Client client = new Client(UUID.randomUUID(), "John", "Doe", "Address1", "Passport1");
        IBank bank = centralBank.createBank("Test Bank", bankInfo);
        UUID account = bank.createAccount(client, BigDecimal.valueOf(1000.0));

        List<IBank> clientBanks = centralBank.getBanks();
        Assertions.assertTrue(clientBanks.contains(bank));

    }
    @Test
    void testSkipDay() {
        timeMachine.addObserverBank(bank1);
        timeMachine.addObserverBank(bank2);

        LocalDate initialDate = timeMachine.getCurrentDate();
        timeMachine.skipDay();

        LocalDate newDate = initialDate.plusDays(1);
        Assertions.assertEquals(newDate, timeMachine.getCurrentDate());
    }
}