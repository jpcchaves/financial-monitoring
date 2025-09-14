package com.financialmonitoring.transactionservice.infra.repository.jpa;

import com.financialmonitoring.transactionservice.infra.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaRepository extends MongoRepository<Transaction, String> {

}
