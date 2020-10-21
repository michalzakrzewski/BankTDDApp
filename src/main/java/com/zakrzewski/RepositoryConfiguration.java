package com.zakrzewski;

import com.zakrzewski.annotations.InMemoryRepository;
import com.zakrzewski.repositories.InMemoryClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;

@Configuration
public class RepositoryConfiguration {

    @Bean
    @InMemoryRepository
    public InMemoryClientRepository repository(){
        return new InMemoryClientRepository(new LinkedList<>());
    }
}
