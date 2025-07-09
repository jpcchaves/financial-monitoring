package com.financialmonitoring.orchestrator.core.saga.transfer.workflow;

import com.financialmonitoring.orchestrator.core.saga.transfer.activity.TransactionSagaActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;
import org.springframework.util.StringUtils;

@WorkflowImpl(taskQueues = "TransactionSagaTaskQueue")
public class TransactionWorkflowImpl implements TransactionWorkflow {

    private final TransactionSagaActivity transactionSagaActivity =
            Workflow.newActivityStub(
                    TransactionSagaActivity.class,
                    ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(2)).build());

    @Override
    public void startSaga(String payload) {
        String message = transactionSagaActivity.executeTransactionSaga(payload);

        Workflow.await(() -> StringUtils.hasText(message));

        System.out.println("Finished!");
    }
}
