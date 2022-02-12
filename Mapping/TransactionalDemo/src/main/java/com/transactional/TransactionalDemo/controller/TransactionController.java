package com.transactional.TransactionalDemo.controller;

import com.transactional.TransactionalDemo.TransactionalDemoApplication;
import com.transactional.TransactionalDemo.entity.Transaction;
import com.transactional.TransactionalDemo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/rollBackOn")
    public Transaction rollBackOn(@RequestBody Transaction transaction) {

        try {
            transactionService.saveTransRollbackOn(transaction);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transaction;
    }


    @PostMapping("/dontRollBackOn")
    public Transaction dontRollBackOn(@RequestBody Transaction transaction) {

        try {
            transactionService.saveTransNoRollbackOn(transaction);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transaction;
    }


    @PostMapping("/defaultApproach")
    public Transaction defaultApproach(@RequestBody Transaction transaction) {

        try {
            transactionService.saveDefaultApproach(transaction);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transaction;
    }



}
