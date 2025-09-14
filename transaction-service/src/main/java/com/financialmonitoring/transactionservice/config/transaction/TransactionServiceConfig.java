package com.financialmonitoring.transactionservice.config.transaction;

import com.financialmonitoring.transactionservice.domain.service.TransactionService;
import com.financialmonitoring.transactionservice.port.output.KafkaProducerPort;
import com.financialmonitoring.transactionservice.port.output.TransactionRepositoryPort;
import com.financialmonitoring.transactionservice.utils.JsonUtils;
import com.financialmonitoring.transactionservice.utils.MapperUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionServiceConfig {

    @Bean
    public TransactionService transactionService(
            TransactionRepositoryPort transactionRepositoryPort,
            KafkaProducerPort kafkaProducer,
            MapperUtils mapperUtils,
            JsonUtils jsonUtils) {
        return new TransactionService(transactionRepositoryPort, kafkaProducer, mapperUtils, jsonUtils);
    }
}
