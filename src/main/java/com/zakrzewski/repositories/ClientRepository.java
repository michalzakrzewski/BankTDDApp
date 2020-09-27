package com.zakrzewski.repositories;

import com.zakrzewski.models.ClientModel;

import java.util.List;

public interface ClientRepository {

    void addClient(ClientModel client);
    ClientModel findClientByEmail(String email);
}
