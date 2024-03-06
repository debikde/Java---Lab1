package com.first.lab.MainBanks.Contract;

import com.first.lab.MainBanks.Clients.Client;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IBank extends IbservableObject {
    List<IAccount> getAccounts();
    UUID createAccount(IClient client, BigDecimal balance);
    void processTransactions();
}
