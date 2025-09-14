package com.financialmonitoring.transactionservice.domain.service;

import com.financialmonitoring.transactionservice.adapter.input.rest.dto.TransactionRequestDTO;
import com.financialmonitoring.transactionservice.adapter.input.rest.dto.TransactionTokenDTO;
import com.financialmonitoring.transactionservice.infra.model.Event;
import com.financialmonitoring.transactionservice.infra.model.Transaction;
import com.financialmonitoring.transactionservice.port.input.TransactionUseCases;
import com.financialmonitoring.transactionservice.port.output.KafkaProducerPort;
import com.financialmonitoring.transactionservice.port.output.TransactionRepositoryPort;
import com.financialmonitoring.transactionservice.utils.JsonUtils;
import com.financialmonitoring.transactionservice.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionService implements TransactionUseCases {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepositoryPort transactionJpaRepository;
    private final KafkaProducerPort kafkaProducer;
    private final MapperUtils mapper;
    private final JsonUtils jsonUtils;

    public TransactionService(TransactionRepositoryPort transactionJpaRepository,
                              KafkaProducerPort kafkaProducer, MapperUtils mapper,
                              JsonUtils jsonUtils) {
        this.transactionJpaRepository = transactionJpaRepository;
        this.kafkaProducer = kafkaProducer;
        this.mapper = mapper;
        this.jsonUtils = jsonUtils;
    }

    public TransactionRequestDTO doTransfer(TransactionRequestDTO requestDTO) {
        logger.info("TransactionService doTransfer called");

        Transaction transaction = mapper.map(requestDTO, Transaction.class);
        transaction.setTransactionEventId(generateTransactionEventId());
        transaction.setTransactionToken(requestDTO.getTransactionToken());
        transaction.setReceiverId(requestDTO.getReceiverId());
        transaction.setAmount(requestDTO.getAmount());
        transaction.setTransactionType(requestDTO.getTransactionType());
        transaction.setUserId(requestDTO.getUserId());
        transaction = transactionJpaRepository.save(transaction);
        kafkaProducer.sendEvent(jsonUtils.toJson(mapEvent(transaction)));
        return mapper.map(transaction, TransactionRequestDTO.class);
    }

    public TransactionTokenDTO generateTransactionToken() {
        logger.info("Generating transaction token");
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
