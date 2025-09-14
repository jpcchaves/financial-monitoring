package com.financialmonitoring.transactionservice.adapter.output.repository;

import com.financialmonitoring.transactionservice.infra.model.Transaction;
import com.financialmonitoring.transactionservice.infra.repository.jpa.TransactionJpaRepository;
import com.financialmonitoring.transactionservice.port.output.TransactionRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository implements TransactionRepositoryPort {

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionRepository(TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionJpaRepository.save(transaction);
    }
}
