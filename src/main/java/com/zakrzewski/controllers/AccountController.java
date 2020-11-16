package com.zakrzewski.controllers;

import com.zakrzewski.dto.AccountRequest;
import com.zakrzewski.dto.AccountResponse;
import com.zakrzewski.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/api/account")
    public ResponseEntity<AccountResponse> findById(@RequestParam Long id) {
        final AccountResponse account = accountService.findAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/api/account")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createAccount(@RequestBody AccountRequest account) {
        accountService.saveAccount(account);
    }


}
