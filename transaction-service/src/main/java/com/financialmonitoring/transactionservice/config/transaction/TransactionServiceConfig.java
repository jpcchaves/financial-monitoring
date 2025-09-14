package com.financialmonitoring.transactionservice.config.transaction;

import com.financialmonitoring.transactionservice.core.producer.KafkaProducer;
import com.financialmonitoring.transactionservice.core.utils.JsonUtils;
import com.financialmonitoring.transactionservice.core.utils.MapperUtils;
import com.financialmonitoring.transactionservice.domain.service.TransactionService;
import com.financialmonitoring.transactionservice.port.output.TransactionRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionServiceConfig {

    @Bean
    public TransactionService transactionService(
            TransactionRepositoryPort transactionRepositoryPort,
            KafkaProducer kafkaProducer,
            MapperUtils mapperUtils, JsonUtils jsonUtils) {
        return new TransactionService(transactionRepositoryPort, kafkaProducer, mapperUtils, jsonUtils);
    }
}
