package com.financialmonitoring.balanceservice.core.consumer;

import com.financialmonitoring.balanceservice.core.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final JsonUtils jsonUtils;

    public KafkaConsumer(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.balance-check-success}")
    public void consumeSuccessTopic(String payload) {
        logger.info("Receiving success event {} from balance-check-success topic", payload);
        // TODO: implement service to process this event
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.balance-check-fail}")
    public void consumeFailTopic(String payload) {
        logger.info("Receiving rollback event {} from balance-check-fail topic", payload);
        // TODO: implement service to process this event
    }
}
