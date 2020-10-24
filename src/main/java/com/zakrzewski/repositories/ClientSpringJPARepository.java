package com.zakrzewski.repositories;

import com.zakrzewski.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientSpringJPARepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.email= :email")
    Client findClientByEmail(@Param("email") String email);

    List<Client> findClientByName(String name);

    Page<Client> findClientByName(String name, Pageable pageable);
}
