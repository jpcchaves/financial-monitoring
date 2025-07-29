package com.financialmonitoring.transactionservice.core.service;

import com.financialmonitoring.transactionservice.core.dto.TransactionRequestDTO;
import com.financialmonitoring.transactionservice.core.dto.TransactionTokenDTO;
import com.financialmonitoring.transactionservice.core.model.Event;
import com.financialmonitoring.transactionservice.core.model.Transaction;
import com.financialmonitoring.transactionservice.core.producer.KafkaProducer;
import com.financialmonitoring.transactionservice.core.repository.TransactionRepository;
import com.financialmonitoring.transactionservice.core.utils.JsonUtils;
import com.financialmonitoring.transactionservice.core.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private static final SecureRandom secureRandom = new SecureRandom();

    private final TransactionRepository transactionRepository;
    private final KafkaProducer kafkaProducer;
    private final MapperUtils mapper;
    private final JsonUtils jsonUtils;

    public TransactionService(
            TransactionRepository transactionRepository,
            KafkaProducer kafkaProducer,
            MapperUtils mapper,
            JsonUtils jsonUtils) {
        this.transactionRepository = transactionRepository;
        this.kafkaProducer = kafkaProducer;
        this.mapper = mapper;
        this.jsonUtils = jsonUtils;
    }

    public TransactionRequestDTO doTransfer(TransactionRequestDTO requestDTO) {
        Transaction transaction = mapper.map(requestDTO, Transaction.class);
        transaction.setTransactionEventId(generateTransactionEventId());
        transaction.setTransactionToken(requestDTO.getTransactionToken());
        transaction.setReceiverId(requestDTO.getReceiverId());
        transaction.setAmount(requestDTO.getAmount());
        transaction.setTransactionType(requestDTO.getTransactionType());
        transaction.setUserId(requestDTO.getUserId());
        transaction = transactionRepository.save(transaction);
        kafkaProducer.sendEvent(jsonUtils.toJson(mapEvent(transaction)));
        return mapper.map(transaction, TransactionRequestDTO.class);
    }

    public TransactionTokenDTO generateTransactionToken() {
        return new TransactionTokenDTO(
                UUID.randomUUID().toString().toUpperCase().replace("-", ""));
    }

    private Event mapEvent(Transaction transaction) {
        Event event = Event.builder()
                .eventId(transaction.getTransactionEventId())
                .transactionId(transaction.getId())
                .transactionToken(transaction.getTransactionToken())
                .payload(jsonUtils.toJson(transaction))
                .createdAt(LocalDateTime.now())
                .build();

        return event;
    }

    private String generateTransactionEventId() {
        return Instant.now()
                .toEpochMilli()
                + "_"
                + UUID.randomUUID();
    }
}
