package com.transactional.TransactionalDemo.service.impl;

import com.transactional.TransactionalDemo.entity.Transaction;
import com.transactional.TransactionalDemo.repository.TransactionalRepository;
import com.transactional.TransactionalDemo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Repository
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionalRepository transactionalRepository;

    @Override
    @Transactional(rollbackOn = {SQLException.class})
    public void saveTransRollbackOn(Transaction transaction) throws SQLException {

        transactionalRepository.save(transaction);

        throw new SQLException("Throwing exception for demoing rollback");
    }

    @Override
    @Transactional(dontRollbackOn = {SQLException.class})
    public void saveTransNoRollbackOn(Transaction transaction) throws SQLException {

        transactionalRepository.save(transaction);
        throw new SQLException("Throwing exception for demoing rollback");
    }


    @Override
    @Transactional
    public void saveDefaultApproach(Transaction transaction) throws SQLException {

        transactionalRepository.save(transaction);
        throw new SQLException("Throwing exception for demoing rollback");
    }

}
