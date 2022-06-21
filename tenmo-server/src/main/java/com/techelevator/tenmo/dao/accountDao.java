package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface accountDao {

    BigDecimal getBalance(String username);

    Account findAccountByUserId(Long userId) throws Exception;
}
