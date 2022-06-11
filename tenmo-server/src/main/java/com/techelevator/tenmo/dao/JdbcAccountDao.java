package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDao implements accountDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public double getBalance(String username) {
        String sql = "select balance from account " +
                "join tenmo_user on account.user_id= tenmo_user.user_id " +
                "WHERE username = ? ";

        Double balance = jdbcTemplate.queryForObject(sql,Double.class,username);
        return balance;
    }
}
