package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    List<Transaction> findTransactionsByUserId(Long userId);

    Transaction findTransactionByTransferId(Long transferId) throws Exception;

    boolean create(long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount);
}
