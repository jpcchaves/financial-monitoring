package com.financialmonitoring.orchestrator.core.saga.transfer.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TransactionWorkflow {

    @WorkflowMethod
    void startSaga(String payload);
}
