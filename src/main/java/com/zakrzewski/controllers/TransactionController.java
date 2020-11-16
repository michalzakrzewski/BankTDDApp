package com.zakrzewski.controllers;

import com.zakrzewski.dto.TransactionRequest;
import com.zakrzewski.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    @PostMapping(path = "/api/transaction")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransactionRequest request) {
        transactionService.createTransaction(request);
    }
}
