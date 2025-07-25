package com.financialmonitoring.fraudservice.core.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.orchestrator}")
    private String orchestratorTopic;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String payload) {
        try {
            logger.info("Event sent to Kafka topic: {}", orchestratorTopic);
            kafkaTemplate.send(orchestratorTopic, payload);
        } catch (Exception e) {
            logger.error("Failed to send event to Kafka topic: {} with payload {}", orchestratorTopic, payload, e);
        }
    }
}
