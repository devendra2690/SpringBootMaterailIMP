package com.transactional.TransactionalDemo.entity;

import javax.persistence.*;

@Entity
@Table(name = "transaction_entity")
public class Transaction {

     @Id
     @GeneratedValue
     private long transactionId;

     @Column
     private String amount;

     @Column
     private String transactionStatus;

     @Column
     private String transactionType;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
