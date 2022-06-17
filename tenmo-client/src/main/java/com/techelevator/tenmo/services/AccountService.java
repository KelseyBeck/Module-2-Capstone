package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
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

    public Account getAccountByUser(AuthenticatedUser user, Long userId){
    HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
    HttpEntity<Account> entity = new HttpEntity<>(headers);
    ResponseEntity<Account> response = restTemplate.exchange(baseUrl+"accounts/"+ userId, HttpMethod.GET, entity, Account.class);
        return response.getBody();
}

    public void updateAccountBalance(AuthenticatedUser user, Account account){
    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
    HttpEntity<Account> entity = new HttpEntity<>(account,headers);
    restTemplate.exchange(baseUrl+"accounts/"+ account.getUser_id(), HttpMethod.PUT, entity, Account.class);
}


}
