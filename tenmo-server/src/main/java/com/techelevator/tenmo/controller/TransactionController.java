package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/transfers")
@PreAuthorize("isAuthenticated()")

public class TransactionController {

        @Autowired
        private TransactionDao transactionDao;


        @GetMapping(value = "/{user_id}")
        public List<Transaction> findTransactionsByUserId(@PathVariable Long id) throws Exception {
        List<Transaction> transactions=null;
        transactions =transactionDao.findTransactionsByUserId(id);
        return transactions;
    }


        @GetMapping(value = "/{id}")
        public Transaction findTransactionByTransferId(@PathVariable Long id) throws Exception {
            Transaction transaction=null;
            transaction = transactionDao.findTransactionByTransferId(id);
            return transaction;
        }
}
