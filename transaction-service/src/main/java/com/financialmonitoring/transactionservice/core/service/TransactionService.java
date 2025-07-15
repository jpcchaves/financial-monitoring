package com.financialmonitoring.transactionservice.core.service;

import com.financialmonitoring.transactionservice.core.dto.TransactionRequestDTO;
import com.financialmonitoring.transactionservice.core.dto.TransactionTokenDTO;
import com.financialmonitoring.transactionservice.core.model.Event;
import com.financialmonitoring.transactionservice.core.model.Transaction;
import com.financialmonitoring.transactionservice.core.producer.KafkaProducer;
import com.financialmonitoring.transactionservice.core.repository.TransactionRepository;
import com.financialmonitoring.transactionservice.core.utils.JsonUtils;
import com.financialmonitoring.transactionservice.core.utils.MapperUtils;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
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
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return new TransactionTokenDTO(
                Base64.getUrlEncoder()
                        .withoutPadding()
                        .encodeToString(bb.array()));
    }

    private Event mapEvent(Transaction transaction) {
        return Event.builder()
                .eventId(transaction.getTransactionEventId())
                .transactionId(transaction.getId())
                .transactionToken(transaction.getTransactionToken())
                .payload(jsonUtils.toJson(transaction))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private String generateTransactionEventId() {
        return Instant.now()
                        .toEpochMilli()
                + "_"
                + UUID.randomUUID();
    }
}
