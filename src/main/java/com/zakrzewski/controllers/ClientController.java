package com.zakrzewski.controllers;

import com.zakrzewski.dto.ClientRequest;
import com.zakrzewski.dto.ClientResponse;
import com.zakrzewski.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(path = "/api/user")
    private ResponseEntity<ClientResponse> findByEmail(@RequestParam String email){
        ClientResponse clientByEmailAddress = clientService.findResponseByEmail(email);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Example_Header", "VALUE");
        return new ResponseEntity<>(clientByEmailAddress, httpHeaders, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/api/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createClient(@RequestBody ClientRequest client){
        clientService.saveClient(client);
    }
}
