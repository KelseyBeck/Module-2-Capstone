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


    @GetMapping(value = "/{transferId}")
    public Transfer findTransactionByTransferId(@PathVariable Long transferId) throws Exception {
        Transfer transfer = null;
        transfer = transferDao.findTransactionByTransferId(transferId);
        return transfer;
    }

    @PostMapping(value = "")
    public void createTransfer(@RequestBody Transfer transfer) {
        transferDao.create(transfer);
    }

}
