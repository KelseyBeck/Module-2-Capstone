package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransactionDao implements TransactionDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAllUserTransactions() {
        return null;
    }

    @Override
    public Transaction findTransactionByTransferId(Long transferId) throws Exception {
            String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_id ILIKE ?;";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
            if (rowSet.next()){
                return mapRowToTransaction(rowSet);
            }
            throw new Exception ("Transaction " + transferId + " was not found.");
    }

    @Override
    public boolean create(long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount) {

        String sql = "INSERT INTO transfer (transferTypeId, transferStatusId, accountFrom, accountTo, amount VALUES (?, ?, ?, ?, ?, ?) RETURNING user_id";
        Long newTransferId;
        try {
            newTransferId = jdbcTemplate.queryForObject(sql, Long.class, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
        } catch (DataAccessException e) {
            return false;
        }
        return false;
    }

    private Transaction mapRowToTransaction(SqlRowSet rs) {
        Transaction transaction = new Transaction();
        transaction.setTransferId(rs.getLong("transfer_id"));
        transaction.setTransferTypeId(rs.getLong("transfer_type_id"));
        transaction.setTransferStatusId(rs.getLong("transfer_transfer_status_id"));
        transaction.setAccountFrom(rs.getLong("account_from"));
        transaction.setAccountTo(rs.getLong("account_to"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        return transaction;
    }

}
