package org.test.bankapp.exception;


import org.test.bankapp.model.Client;

public interface ClientRegistrationListener {
    void onClientAdded(Client c);
}
