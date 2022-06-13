package com.techelevator.tenmo.services;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import  com.techelevator.tenmo.model.AuthenticatedUser;

import java.math.BigDecimal;

public class AccountService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();


    public AccountService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public BigDecimal getBalance(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl+"users/"+user.getUser().getUsername()+"/balance", HttpMethod.GET, entity, BigDecimal.class);
        return response.getBody().setScale(2);
    }





}
