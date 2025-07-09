package com.financialmonitoring.orchestrator.config.temporal;

import com.financialmonitoring.orchestrator.core.saga.transfer.activity.TransactionSagaActivityImpl;
import com.financialmonitoring.orchestrator.core.saga.transfer.workflow.TransactionWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

    @Bean
    public WorkflowClient workflowClient() {
        return WorkflowClient.newInstance(WorkflowServiceStubs.newLocalServiceStubs());
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient, TransactionSagaActivityImpl activityImpl) {
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);

        Worker worker = factory.newWorker("TransactionSagaTaskQueue");

        worker.registerWorkflowImplementationTypes(TransactionWorkflowImpl.class);
        worker.registerActivitiesImplementations(activityImpl);

        factory.start();
        return factory;
    }
}
