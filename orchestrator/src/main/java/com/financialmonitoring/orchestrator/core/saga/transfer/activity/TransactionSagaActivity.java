package com.financialmonitoring.orchestrator.core.saga.transfer.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface TransactionSagaActivity {

    @ActivityMethod
    String executeTransactionSaga(String payload);
}
