package org.test.bankapp.model;

import org.test.bankapp.exception.ClientExistsException;

import java.util.*;

public class Bank implements Report {
    private Set<Client> clients = new HashSet<Client>();
    private List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
    private Map<String, Client> clientCache = new TreeMap<String, Client>();

    private interface ClientRegistrationListener {
        void onClientAdded(Client c);
    }

    private class PrintClientListener implements ClientRegistrationListener {
        public void onClientAdded(Client client) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(" Client registration report ");
            System.out.println("  Client name       : " + client.getName());
            System.out.format("  Client overdraft  : %.2f\n", client.getInitialOverdraft());
            System.out.format("  Client balance    : %.2f\n", client.getBalance());
            System.out.println("  Active account    :");
            if (client.getActiveAccount() != null) {
                client.getActiveAccount().printReport();
            }
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    private class EmailNotificationListener implements ClientRegistrationListener {
        public void onClientAdded(Client client) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("Notification email for client \"" + client.getName() + "\"… to be sent");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        }
    }

    private void registerEvent(ClientRegistrationListener listener) {
        listeners.add(listener);
    }

    public Bank() {
        registerEvent(new PrintClientListener());
        registerEvent(new EmailNotificationListener());
    }

    private void checkDuplicateName(Client client) throws ClientExistsException {
        for (Client tmpClient : clients) {
            if (tmpClient.getName().equals(client.getName())) {
                throw new ClientExistsException();
            }
        }
    }

    public void addClient(Client client) throws ClientExistsException {
        checkDuplicateName(client);
        // TODO: cache should take into consideration client.gender
        clientCache.put(client.getName(), client);
        clients.add(client);
        for (ClientRegistrationListener listener : listeners) {
            listener.onClientAdded(client);
        }
    }

    public void removeClient(Client c) {
        clients.remove(c);
        // TODO - remove action be name only
        clientCache.remove(c.getName());
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Map<String, Client> getClientCache() {
        return clientCache;
    }

    public void printReport() {
        float bankBalance = 0.f;
        for (Client client : clients) {
            bankBalance += client.getBalance();
        }
        System.out.println("\n\n\n\nBank report  :");
        System.out.println("Report date  : " + new Date());
        System.out.printf("Bank balance : %.2f\n", bankBalance);
        System.out.printf("Bank kredit : %.2f\n", BankReport.getBankCreditSum(this));
        System.out.println("Client lists (client count:" + BankReport.getNumberOfClients(this) +
                "" + " client account count: " + BankReport.getAccountsNumber(this) + "):");
        int i = 1;
        for (Client client : BankReport.getClientsSorted(this)) {
            System.out.println("==============================================================");
            System.out.println("Clinet # [" + i + "]");
            System.out.println("==============================================================");
            client.printReport();
            System.out.println("==============================================================");
            i++;
        }
        System.out.println("Stats by city:");
        Map<String, List<Client>> mapClinetOfCities = BankReport.getClientsByCity(this);
        for (String city : mapClinetOfCities.keySet()) {
            System.out.println("City: " + city);
            for (Client client : mapClinetOfCities.get(city)) {
                client.printReport();
            }
        }
        System.out.println(BankReport.getClientsByCity(this));
    }
}
