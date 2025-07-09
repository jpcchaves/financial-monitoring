package com.financialmonitoring.orchestrator.core.saga.transfer.consumer;

import com.financialmonitoring.orchestrator.core.saga.transfer.workflow.TransactionWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionSagaConsumer {

    private final WorkflowClient workflowClient;

    public TransactionSagaConsumer(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @KafkaListener(groupId = "temporal-teste-group", topics = "teste-temporal")
    public void consumeTemporalTest(String payload) {
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("TransactionSagaTaskQueue")
                .build();

        TransactionWorkflow workflow = workflowClient.newWorkflowStub(TransactionWorkflow.class, options);
        WorkflowClient.start(workflow::startSaga, payload);
    }
}
