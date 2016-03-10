package org.test.bankapp.service;

import org.test.bankapp.exception.ClientExistsException;
import org.test.bankapp.exception.NotEnoughFundsException;
import org.test.bankapp.model.Account;
import org.test.bankapp.model.Bank;
import org.test.bankapp.model.Client;

/**
 * который будет осуществлять добавление клиента и прочие сервисные операции
 */
public interface BankService {
    void addClient(Bank bank, Client client) throws ClientExistsException;

    void removeClient(Bank bank, Client client);

    void addAccount(Client client, Account account);

    void setActiveAccount(Client client, Account account);

    Client findClientByName(Bank bank, String clientName);

    Client getClient(Bank bank, String clientName);

    Account createAccount(Client client, String accountType);

    void deposit(Client client, float x);

    void withdraw(Client client, float x) throws NotEnoughFundsException;

    public void printReport(Bank bank);

    public void printReport(Client client);
}
