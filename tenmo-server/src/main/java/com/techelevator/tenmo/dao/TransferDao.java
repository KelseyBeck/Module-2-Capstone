package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransactions();
    List<Transfer> findTransactionsByUserId(Long userId);

    Transfer findTransactionByTransferId(Long transferId) throws Exception;

    void create(Transfer transfer);


    String findTransactionTypeByTransferId(Long transferId);

    String findTransactionStatusByTransferId(Long transferId);

    String findTransactionSenderByTransferId(Long transferId);

    String findTransactionRecipientByTransferId(Long transferId);
}
