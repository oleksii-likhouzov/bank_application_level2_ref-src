package org.test.bankapp.service;

import org.test.bankapp.exception.ClientExistsException;
import org.test.bankapp.exception.NotEnoughFundsException;
import org.test.bankapp.model.Account;
import org.test.bankapp.model.Bank;
import org.test.bankapp.model.Client;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BankServiceImpl implements BankService {
    public final static String BINARY_FILE_NAME = "./clients/client.bin";
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
        return bank.getClientCache().get(clientName);
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

    private void loadFile(Bank bank, File file) throws IOException {
        BufferedReader buferReader = new BufferedReader(new FileReader(file));

        while (buferReader.ready()) {
            String line = buferReader.readLine();
            Map<String, String> feed = new HashMap<String, String>();
            for (String item : line.split(";")) {
                String pairs[] = item.split("=");
                if (pairs.length != 2) {
                    System.out.println("Not valid value:\"" + item + "\" in line (" + line + ")");
                    continue;
                }
                feed.put(pairs[0], pairs[1]);
            }
            if (feed.size() != 0) {
                bank.parseFeed(feed);
            }
        }
        buferReader.close();
    }

    public void loadFeed(Bank bank, String folderName) {
        File folder = new File(folderName);
        if (!folder.exists()) {
            throw new RuntimeException("Folder " + folderName + " is not exists.");
        }
        if (!folder.isDirectory()) {
            throw new RuntimeException(folderName + " is not a directory.");
        }
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                continue;
            }
            System.out.println("File name: [" + file.getAbsolutePath() + "]");
            try {
                loadFile(bank, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveClient(Client client) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(BINARY_FILE_NAME)));

            outputStream.writeObject(client);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client loadClient() {
        Client client= null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(BINARY_FILE_NAME)));

            try {
                client = (Client) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }
}
