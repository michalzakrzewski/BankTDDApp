package com.zakrzewski.services;

import com.zakrzewski.dto.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class CurrencyService {

    private RestTemplate restTemplate;

    @Autowired
    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrencyResponse getCurrencyRates(){
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("base", "USD");
        ResponseEntity<CurrencyResponse> response = restTemplate.getForEntity("https://api.exchangeratesapi.io/latest", CurrencyResponse.class, uriVariables);
        return response.getBody();

    }
}
