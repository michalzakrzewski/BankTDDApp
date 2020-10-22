package com.zakrzewski.repositories;

import com.zakrzewski.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientSpringJPARepository extends JpaRepository<Client, Long> {

    Client findClientByEmail(String emailAddress);
}
