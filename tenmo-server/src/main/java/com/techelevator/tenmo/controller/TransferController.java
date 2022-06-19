package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private TransferDao transferDao;


    @GetMapping(value = "")
    public List<Transfer> getAllTransactions() {
        return transferDao.getAllTransactions();
    }

    @GetMapping(value = "/users/{userId}")
    public List<Transfer> findTransactionsByUserId(@PathVariable Long userId) throws Exception {
        List<Transfer> transactions = null;
        transactions = transferDao.findTransactionsByUserId(userId);
        return transactions;
    }

    @GetMapping(value = "/{transfer_id}")
    public Transfer getTransfersByTransferId(@PathVariable Long transfer_id) throws Exception {
        return transferDao.findTransactionByTransferId(transfer_id);
    }

    @GetMapping(value = "/{transferId}/info/transfer_type")
    public String findTransactionTypeByTransferId(@PathVariable Long transferId) throws Exception {
        String transferType = "null";
        transferType = transferDao.findTransactionTypeByTransferId(transferId);
        return transferType;
    }

    @GetMapping(value = "/{transferId}/info/transfer_status")
    public String findTransactionStatusByTransferId(@PathVariable Long transferId) throws Exception {
        String transferStatus = "null";
        transferStatus = transferDao.findTransactionStatusByTransferId(transferId);
        return transferStatus;
    }

    @GetMapping(value = "/{transferId}/info/transfer_sender")
    public String findTransactionSenderByTransferId(@PathVariable Long transferId) throws Exception {
        String transferSender = "null";
        transferSender = transferDao.findTransactionSenderByTransferId(transferId);
        return transferSender;
    }

    @GetMapping(value = "/{transferId}/info/transfer_recipient")
    public String findTransactionRecipientByTransferId(@PathVariable Long transferId) throws Exception {
        String transferRecipient = "null";
        transferRecipient = transferDao.findTransactionRecipientByTransferId(transferId);
        return transferRecipient;
    }

    @PostMapping(value = "")
    public void createTransfer(@RequestBody Transfer transfer) {
        transferDao.create(transfer);
    }

}
