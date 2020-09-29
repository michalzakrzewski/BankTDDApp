package com.zakrzewski.repositories;

import com.zakrzewski.entity.Client;

public interface ClientRepository {

    void addClient(Client client);
    Client findClientByEmail(String email);
}
