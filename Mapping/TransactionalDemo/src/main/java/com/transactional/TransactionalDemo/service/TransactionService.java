package com.transactional.TransactionalDemo.service;

import com.transactional.TransactionalDemo.entity.Transaction;

import java.sql.SQLException;

public interface TransactionService {

    public void saveTransRollbackOn(Transaction transaction) throws SQLException;
    public void saveTransNoRollbackOn(Transaction transaction) throws SQLException;
    public void saveDefaultApproach(Transaction transaction) throws SQLException;

}
