package com.zakrzewski.controllers;

import com.zakrzewski.dto.CurrencyResponse;
import com.zakrzewski.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    @GetMapping(path = "/api/currency")
    public ResponseEntity<CurrencyResponse> getCurrencyRate(){
        CurrencyResponse currencyRates = currencyService.getCurrencyRates();
        return new ResponseEntity<>(currencyRates, HttpStatus.ACCEPTED);
    }
}

