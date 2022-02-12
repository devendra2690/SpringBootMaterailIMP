package com.transactional.TransactionalDemo.repository;

import com.transactional.TransactionalDemo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionalRepository extends JpaRepository<Transaction,Long> {


}
