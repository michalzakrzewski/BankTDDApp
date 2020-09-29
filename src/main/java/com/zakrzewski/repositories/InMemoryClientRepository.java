package com.zakrzewski.repositories;

import com.zakrzewski.entity.Client;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class InMemoryClientRepository implements ClientRepository {

    private List<Client> clients;

    public InMemoryClientRepository(List<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public List<Client> findAllClients() {
        return clients;
    }

    public Client findClientByEmail(final String email) {
        return clients.stream()
                .filter(client -> Objects.equals(client.getEmailAddress(), email))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Client with following email: %s not found!", email)));
    }
}
