package com.financialmonitoring.orchestrator.core.service;

import com.financialmonitoring.commonlib.dto.EventDTO;
import com.financialmonitoring.commonlib.dto.HistoryDTO;
import com.financialmonitoring.commonlib.enums.EventSource;
import com.financialmonitoring.commonlib.enums.SagaStatus;
import com.financialmonitoring.commonlib.enums.TransactionSagaTopics;
import com.financialmonitoring.orchestrator.core.producer.KafkaProducer;
import com.financialmonitoring.orchestrator.core.saga.SagaExecutionController;
import com.financialmonitoring.orchestrator.core.utils.JsonUtils;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrchestratorService {

    private static final Logger logger = LoggerFactory.getLogger(OrchestratorService.class);

    private final KafkaProducer producer;
    private final SagaExecutionController sagaExecutionController;
    private final JsonUtils jsonUtils;

    public OrchestratorService(KafkaProducer producer,
            SagaExecutionController sagaExecutionController,
            JsonUtils jsonUtils) {
        this.producer = producer;
        this.sagaExecutionController = sagaExecutionController;
        this.jsonUtils = jsonUtils;
    }

    public void startSaga(EventDTO event) {
        logger.info("STARTING SAGA WITH EVENT ID: {}", event.getId());
        event.setSource(EventSource.ORCHESTRATOR);
        event.setStatus(SagaStatus.SUCCESS);
        TransactionSagaTopics topic = getNextTopic(event);
        addHistory(event, "Saga started!");
        producer.sendEvent(jsonUtils.toJson(event), topic.getTransactionTopic());
    }

    public void continueSaga(EventDTO event) {
        TransactionSagaTopics topic = getNextTopic(event);
        logger.info("SAGA CONTINUING FOR EVENT: {}", event.getId());
        producer.sendEvent(jsonUtils.toJson(event), topic.getTransactionTopic());
    }

    public void finishSuccess(EventDTO event) {
        event.setSource(EventSource.ORCHESTRATOR);
        event.setStatus(SagaStatus.SUCCESS);
        logger.info("SAGA FINISHED SUCCESSFULLY FOR EVENT {}!", event.getId());
        addHistory(event, "Saga finished successfully!");
        notifyFinishedSaga(event);
    }

    public void finishFail(EventDTO event) {
        event.setSource(EventSource.ORCHESTRATOR);
        event.setStatus(SagaStatus.FAIL);
        logger.info("SAGA FINISHED WITH ERRORS FOR EVENT {}!", event.getId());
        addHistory(event, "Saga finished with errors!");
        notifyFinishedSaga(event);
    }

    private TransactionSagaTopics getNextTopic(EventDTO event) {
        return sagaExecutionController.getNextTopic(event);
    }

    private void addHistory(EventDTO event, String message) {
        var history = HistoryDTO
                .builder()
                .source(event.getSource())
                .status(event.getStatus())
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
        event.addToHistory(history);
    }

    private void notifyFinishedSaga(EventDTO event) {
        producer.sendEvent(jsonUtils.toJson(event), TransactionSagaTopics.NOTIFY_ENDING.getTransactionTopic());
    }
}
