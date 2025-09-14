package com.financialmonitoring.transactionservice.adapter.input.kafka;

import com.financialmonitoring.transactionservice.core.service.EventService;
import com.financialmonitoring.transactionservice.core.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final JsonUtils jsonUtils;
    private final EventService eventService;

    public KafkaConsumer(JsonUtils jsonUtils,
            EventService eventService) {
        this.jsonUtils = jsonUtils;
        this.eventService = eventService;
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.notify-ending}")
    public void consumeNotifyEndingTopic(String payload) {
        logger.info("Receiving ending notification event {} from notify-ending topic", payload);
        eventService.notifyEnding(jsonUtils.toEvent(payload));
    }
}
