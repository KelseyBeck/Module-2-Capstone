package com.techelevator.tenmo.services;

import org.apiguardian.api.API;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import  com.techelevator.tenmo.model.UserCredentials;
import  com.techelevator.tenmo.model.AuthenticatedUser;
public class AccountService {

    private final String baseUrl="http://localhost:8080/users/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken=null;

    public void setAuthToken(String authToken){
        this.authToken=authToken;
    }

    public Double getBalance(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Double> response = restTemplate.exchange(baseUrl+user.getUser().getUsername()+"/balance", HttpMethod.GET, entity, Double.class);
        return response.getBody();

    }





}
