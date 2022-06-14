package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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

    @Override
    public Account getAccount(Long userId){
        String sql = "select * from account where user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        Account account=null;
        if(rowSet.next()){
           account = mapRowToAccount(rowSet);
        }
        return account;
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
