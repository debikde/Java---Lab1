package com.first.lab.MainBanks.Clients.Builder;

import com.first.lab.MainBanks.Clients.Client;
import com.first.lab.MainBanks.Contract.IClient;
import com.first.lab.MainBanks.Contract.IObserverObject;

import java.util.UUID;

public class ClientBuilder {
    private UUID id;
    private String name;
    private String lastName;
    private String address;
    private String passport;

    public ClientBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public ClientBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ClientBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ClientBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public ClientBuilder setPassport(String passport) {
        this.passport = passport;
        return this;
    }

    public IClient build() {
        return new Client(id, name, lastName, address, passport);
    }
}
