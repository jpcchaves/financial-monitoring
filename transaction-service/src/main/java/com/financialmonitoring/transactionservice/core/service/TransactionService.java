package com.financialmonitoring.transactionservice.core.service;

import com.financialmonitoring.transactionservice.core.dto.TransactionDTO;
import com.financialmonitoring.transactionservice.core.model.Transaction;
import com.financialmonitoring.transactionservice.core.producer.KafkaProducer;
import com.financialmonitoring.transactionservice.core.repository.TransactionRepository;
import com.financialmonitoring.transactionservice.core.utils.JsonUtils;
import com.financialmonitoring.transactionservice.core.utils.MapperUtils;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final KafkaProducer kafkaProducer;
    private final MapperUtils mapper;
    private final JsonUtils jsonUtils;

    public TransactionService(TransactionRepository transactionRepository,
            KafkaProducer kafkaProducer,
            MapperUtils mapper,
            JsonUtils jsonUtils) {
        this.transactionRepository = transactionRepository;
        this.kafkaProducer = kafkaProducer;
        this.mapper = mapper;
        this.jsonUtils = jsonUtils;
    }

    public TransactionDTO doTransfer(TransactionDTO requestDTO) {
        Transaction transaction = mapper.map(requestDTO, Transaction.class);
        transaction.setTransactionEventId(generateTransactionEventId());
        transaction.setCreatedAt(LocalDateTime.now());
        kafkaProducer.sendEvent(jsonUtils.toJson(transaction));
        transaction = transactionRepository.save(transaction);
        return mapper.map(transaction, TransactionDTO.class);
    }

    private String generateTransactionEventId() {
        return Instant.now().toEpochMilli() + "_" + UUID.randomUUID();
    }
}
