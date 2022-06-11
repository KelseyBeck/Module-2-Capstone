package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/users")
public class AccountController {

    @Autowired
    private JdbcAccountDao jdbcAccountDao;

    @GetMapping(value = "/{username}/balance")
    public double getBalance(@PathVariable String username){
        return jdbcAccountDao.getBalance(username);
    }
}
