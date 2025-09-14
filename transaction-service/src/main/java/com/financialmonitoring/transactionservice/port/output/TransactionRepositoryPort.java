package com.financialmonitoring.transactionservice.port.output;

import com.financialmonitoring.transactionservice.infra.model.Transaction;

public interface TransactionRepositoryPort {
    Transaction save(Transaction transaction);
}
