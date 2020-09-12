package com.zakrzewski.repositories;

import com.zakrzewski.models.ClientModel;

import java.util.Set;

public interface ClientRepository {

    void addClient(ClientModel client);
    Set<ClientModel> findAllClients();
    ClientModel findClientByEmail(String email);
}
