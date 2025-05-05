package com.financialmonitoring.transactionservice.core.repository;

import com.financialmonitoring.transactionservice.core.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

}
