package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.objenesis.strategy.BaseInstantiatorStrategy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class UserService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    public UserService(String baseUrl) {
        this.baseUrl = baseUrl;
    }



    public User[] getUsers(AuthenticatedUser loggedInUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loggedInUser.getToken());
        HttpEntity<User> entity = new HttpEntity<>(headers);
        ResponseEntity<User[]> response = restTemplate.exchange(baseUrl+"users", HttpMethod.GET, entity, User[].class);
        return response.getBody();
    }

    public User findUser(AuthenticatedUser loggedInUser,long userId){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loggedInUser.getToken());
        HttpEntity<User> entity = new HttpEntity<>(headers);
        //// url (api + users/id)..
        String url = baseUrl + "users/"+ (int)userId;
            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);
            return response.getBody();
    }



}
