package com.zakrzewski.repositories;

import com.zakrzewski.annotations.InMemoryRepository;
import com.zakrzewski.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Repository
public class InMemoryClientRepository implements ClientRepository {

    private List<Client> clients;

    public InMemoryClientRepository(List<Client> clients) {
        this.clients = clients;
    }

    public void saveClient(Client client) {
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
