package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> getAllTransactions() {
        List<Transfer> transactionsList=new ArrayList<>();
        String sql="Select * from transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Transfer transaction = mapRowToTransaction(results);
            transactionsList.add(transaction);
        }
        return transactionsList;
    }

    @Override
    public List<Transfer> findTransactionsByUserId(Long userId) {
        List<Transfer> transactions = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer Join account on transfer.account_from = account.account_id WHERE account_from = (select account.account_id from account where account.user_id = ? ) or account_to = (select account.account_id from account where account.user_id = ? );";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId,userId);
        while(results.next()) {
            Transfer transaction = mapRowToTransaction(results);
            transactions.add(transaction);
        }
        return transactions;
    }


    @Override
    public Transfer findTransactionByTransferId(Long transferId) throws Exception {
            String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_id = ?;";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
            if (rowSet.next()){
                return mapRowToTransaction(rowSet);
            }
            throw new Exception ("Transaction " + transferId + " was not found.");
    }

    @Override
    public void create(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to,amount) VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
        Long newTransferId;
            newTransferId = jdbcTemplate.queryForObject(sql, Long.class,transfer.getTransferTypeId(),transfer.getTransferStatusId(),transfer.getAccountFrom(),transfer.getAccountTo(),transfer.getAmount());
            transfer.setTransferId(newTransferId);
    }

    private Transfer mapRowToTransaction(SqlRowSet rs) {
        Transfer transaction = new Transfer();
        transaction.setTransferId(rs.getLong("transfer_id"));
        transaction.setTransferTypeId(rs.getLong("transfer_type_id"));
        transaction.setTransferStatusId(rs.getLong("transfer_status_id"));
        transaction.setAccountFrom(rs.getLong("account_from"));
        transaction.setAccountTo(rs.getLong("account_to"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        return transaction;
    }

}
