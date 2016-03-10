package org.test.bankapp.service;

import org.test.bankapp.exception.ClientExistsException;
import org.test.bankapp.exception.NotEnoughFundsException;
import org.test.bankapp.model.Account;
import org.test.bankapp.model.Bank;
import org.test.bankapp.model.Client;

public class BankServiceImpl implements BankService {

    public void addClient(Bank bank, Client client) throws ClientExistsException {
        bank.addClient(client);
    }

    public void removeClient(Bank bank, Client client) {
        bank.removeClient(client);
    }

    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    public Client findClientByName(Bank bank, String clientName) {
        for (Client client : bank.getClients()) {
            if (clientName.equals(client.getName())) {
                return client;
            }
        }
        return null;
    }

    public Client getClient(Bank bank, String clientName) {
        return bank.getClientCache().get(clientName);
    }

    public Account createAccount(Client client, String accountType) {
        return client.createAccount(accountType);
    }

    public void deposit(Client client, float x) {
        client.deposit(x);
    }

    public void withdraw(Client client, float x) throws NotEnoughFundsException {
        client.withdraw(x);
    }

    public void printReport(Bank bank) {
        bank.printReport();
    }

    public void printReport(Client client) {
        client.printReport();
    }
}
