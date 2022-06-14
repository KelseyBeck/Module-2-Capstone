package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/")
public class AccountController {

    @Autowired
    private JdbcAccountDao jdbcAccountDao;

    @GetMapping(value = "users/{username}/balance")
    public double getBalance(@PathVariable String username){
        return jdbcAccountDao.getBalance(username);
    }

    @GetMapping("accounts/{user_id}")
    public Account getAccount(@PathVariable Long user_id){
        return jdbcAccountDao.getAccount(user_id);
    }

    @PutMapping("accounts/{account_id}")
    public BigDecimal updateAccountBalance(@RequestBody Account account, @PathVariable long account_id){
        jdbcAccountDao.updateAccountBalance(account, account_id);
        return null;
    }








        }