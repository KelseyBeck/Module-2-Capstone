package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.util.List;

public interface TransactionDao {

    List<Transaction> findAll();

    Transaction findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
    Transaction findByUserById(Long id);
}
