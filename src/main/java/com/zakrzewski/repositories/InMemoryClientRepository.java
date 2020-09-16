package com.zakrzewski.repositories;

import com.zakrzewski.models.ClientModel;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class InMemoryClientRepository implements ClientRepository {

    private List<ClientModel> clients;

    public InMemoryClientRepository(List<ClientModel> clients) {
        this.clients = clients;
    }

    public void addClient(ClientModel client) {
        clients.add(client);
    }

    public List<ClientModel> findAllClients() {
        return clients;
    }

    public ClientModel findClientByEmail(final String email) {
        return clients.stream()
                .filter(client -> Objects.equals(client.getEmailAddress(), email))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Client with following email: %s not found!", email)));
    }
}
