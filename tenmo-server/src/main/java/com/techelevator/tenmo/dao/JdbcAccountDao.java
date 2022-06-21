package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements accountDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public BigDecimal getBalance(String username) {
        String sql = "select balance from account " +
                "join tenmo_user on account.user_id= tenmo_user.user_id " +
                "WHERE username = ? ";

        BigDecimal balance = jdbcTemplate.queryForObject(sql,BigDecimal.class,username);
        return balance;
    }

    @Override
    public Account findAccountByUserId(Long userId) throws Exception {
        String sql = "select * from account where user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if(rowSet.next()){
           return mapRowToAccount(rowSet);
        }
        throw new Exception( "Account with User id " + userId + " was not found.");
    }

    public void updateAccountBalance(Account account){
        String sql = "update account set balance = ? where user_id = ?";
        jdbcTemplate.update(sql, account.getBalance(),account.getUser_id());

    }


    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccount_id(rs.getLong("account_id"));
        account.setUser_id(rs.getLong("user_id"));
        account .setBalance(rs.getBigDecimal("balance"));
        return account;
    }

}
