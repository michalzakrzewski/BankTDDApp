package com.zakrzewski.controllers;

import com.zakrzewski.entity.Client;
import com.zakrzewski.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BankController {

    private BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping(path = "/api/user")
    private ResponseEntity<Client> findByEmail(@RequestParam String email){
        Client clientByEmailAddress = bankService.findClientByEmailAddress(email);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Example_Header", "VALUE");
        return new ResponseEntity<>(clientByEmailAddress, httpHeaders, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/api/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createClient(@RequestBody Client client){
        bankService.saveClient(client);
    }
}
