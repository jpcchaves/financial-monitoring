package com.financialmonitoring.transactionservice.port.output;

public interface KafkaProducerPort {

    void sendEvent(String payload);
}
