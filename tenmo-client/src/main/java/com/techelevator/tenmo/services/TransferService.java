package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();


    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    public void createTransfer(AuthenticatedUser authenticatedUser,Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer,headers);
        restTemplate.exchange(baseUrl+"transfers/", HttpMethod.POST, entity, Transfer.class);

    }

    public Transfer[] getTransfersByUserId(AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer[]> entity = new HttpEntity<>(headers);
      return   restTemplate.exchange(baseUrl+"transfers/users/"+authenticatedUser.getUser().getId(), HttpMethod.GET, entity, Transfer[].class).getBody();
    }

    public Transfer getTransfersByTransferId(AuthenticatedUser authenticatedUser,Long transferID){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(headers);
        return   restTemplate.exchange(baseUrl+"transfers/"+transferID ,HttpMethod.GET, entity, Transfer.class).getBody();
    }

    public String getTransfersTypeByTransferId(AuthenticatedUser authenticatedUser,Long transferID){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return   restTemplate.exchange(baseUrl+"transfers/"+transferID+"/info/transfer_type" ,HttpMethod.GET, entity, String.class).getBody();
    }

    public String getTransactionSenderByTransferId(AuthenticatedUser authenticatedUser,Long transferID){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return   restTemplate.exchange(baseUrl+"transfers/"+transferID+"/info/transfer_sender" ,HttpMethod.GET, entity, String.class).getBody();
    }

    public String getTransactionRecipientByTransferId(AuthenticatedUser authenticatedUser,Long transferID){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return   restTemplate.exchange(baseUrl+"transfers/"+transferID+"/info/transfer_recipient" ,HttpMethod.GET, entity, String.class).getBody();
    }


    public String getTransactionStatusByTransferId(AuthenticatedUser authenticatedUser,Long transferID){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return   restTemplate.exchange(baseUrl+"transfers/"+transferID+"/info/transfer_status" ,HttpMethod.GET, entity, String.class).getBody();
    }

}
