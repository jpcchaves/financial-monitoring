package com.financialmonitoring.orchestrator.core.saga.transfer.activity;

import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "TransactionSagaTaskQueue")
public class TransactionSagaActivityImpl implements TransactionSagaActivity {

    @Override
    public String executeTransactionSaga(String payload) {
        return "Received transaction event!";
    }
}
