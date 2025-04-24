package com.financialmonitoring.orchestrator.core.saga;

import com.financialmonitoring.commonlib.dto.EventDTO;
import com.financialmonitoring.commonlib.enums.TransactionSagaTopics;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class SagaExecutionController {
    private static final Logger logger = LoggerFactory.getLogger(SagaExecutionController.class);
    private static final String SAGA_LOG = "TRANSACTION_ID: %s | EVENT_ID: %s";

    public SagaExecutionController() {
    }

    public TransactionSagaTopics getNextTopic(EventDTO<?> event) {
        if (ObjectUtils.isEmpty(event.getSource()) || ObjectUtils.isEmpty(event.getStatus())) {
            throw new IllegalArgumentException("Event source or status cannot be empty");
        }

        TransactionSagaTopics topic = findBySourceAndStatus(event);
        logCurrentSaga(event, topic);
        return topic;
    }

    private TransactionSagaTopics findBySourceAndStatus(EventDTO<?> event) {
        return (TransactionSagaTopics) Arrays.stream(SagaHandler.SAGA_HANDLER).filter(row -> isEventSourceAndStatusValid(event, row))
                .map(i -> i[SagaHandler.TOPIC_INDEX])
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Topic not found!"));
    }

    private boolean isEventSourceAndStatusValid(EventDTO<?> event, Object[] row) {
        Object source = row[SagaHandler.EVENT_SOURCE_INDEX];
        Object status = row[SagaHandler.SAGA_STATUS_INDEX];

        return source.equals(event.getSource()) && status.equals(event.getStatus());
    }

    private void logCurrentSaga(EventDTO<?> event, TransactionSagaTopics topic) {
        var sagaId = createSagaId(event);
        var source = event.getSource();
        switch (event.getStatus()) {
            case SUCCESS -> logger.info("### CURRENT SAGA: {} | SUCCESS | NEXT TOPIC {} | {}",
                    source, topic, sagaId);
            case ROLLBACK_PENDING -> logger.info("### CURRENT SAGA: {} | SENDING TO ROLLBACK CURRENT SERVICE | NEXT TOPIC {} | {}",
                    source, topic, sagaId);
            case FAIL -> logger.info("### CURRENT SAGA: {} | SENDING TO ROLLBACK PREVIOUS SERVICE | NEXT TOPIC {} | {}",
                    source, topic, sagaId);
        }
    }

    private String createSagaId(EventDTO<?> event) {
        return String.format(SAGA_LOG, event.getTransactionId(), event.getId());
    }
}
