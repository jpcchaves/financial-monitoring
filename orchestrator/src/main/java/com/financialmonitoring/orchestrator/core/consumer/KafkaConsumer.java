package com.financialmonitoring.orchestrator.core.consumer;

import com.financialmonitoring.orchestrator.core.service.OrchestratorService;
import com.financialmonitoring.orchestrator.core.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final OrchestratorService orchestratorService;
    private final JsonUtils jsonUtils;

    public KafkaConsumer(OrchestratorService orchestratorService,
            JsonUtils jsonUtils) {
        this.orchestratorService = orchestratorService;
        this.jsonUtils = jsonUtils;
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.start-transaction-saga}")
    public void consumeStartSagaTopic(String payload) {
        orchestratorService.startSaga(jsonUtils.toEvent(payload));
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.orchestrator}")
    public void consumeOrchestratorTopic(String payload) {
        orchestratorService.continueSaga(jsonUtils.toEvent(payload));
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.finish-success}")
    public void consumeFinishSuccessTopic(String payload) {
        orchestratorService.finishSuccess(jsonUtils.toEvent(payload));
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.finish-fail}")
    public void consumeFinishFailTopic(String payload) {
        orchestratorService.finishFail(jsonUtils.toEvent(payload));
    }
}
