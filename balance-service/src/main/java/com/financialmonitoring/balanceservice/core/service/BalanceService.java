package com.financialmonitoring.balanceservice.core.service;

import com.financialmonitoring.balanceservice.core.model.Balance;
import com.financialmonitoring.balanceservice.core.model.BalanceCheckLog;
import com.financialmonitoring.balanceservice.core.producer.KafkaProducer;
import com.financialmonitoring.balanceservice.core.repository.BalanceCheckLogRepository;
import com.financialmonitoring.balanceservice.core.repository.BalanceRepository;
import com.financialmonitoring.balanceservice.core.utils.JsonUtils;
import com.financialmonitoring.commonlib.dto.EventDTO;
import com.financialmonitoring.commonlib.dto.HistoryDTO;
import com.financialmonitoring.commonlib.dto.TransactionDTO;
import com.financialmonitoring.commonlib.enums.EventSource;
import com.financialmonitoring.commonlib.enums.SagaStatus;
import com.financialmonitoring.commonlib.exceptions.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    private static final Logger logger = LoggerFactory.getLogger(BalanceService.class);

    private final BalanceRepository balanceRepository;
    private final BalanceCheckLogRepository logRepository;
    private final KafkaProducer kafkaProducer;
    private final JsonUtils jsonUtils;

    public BalanceService(BalanceRepository balanceRepository,
            BalanceCheckLogRepository logRepository,
            KafkaProducer kafkaProducer,
            JsonUtils jsonUtils) {
        this.balanceRepository = balanceRepository;
        this.logRepository = logRepository;
        this.kafkaProducer = kafkaProducer;
        this.jsonUtils = jsonUtils;
    }

    public void doBalanceCheck(EventDTO event) {
        try {
            TransactionDTO transactionDto = (TransactionDTO) event.getPayload();
            // verify if there's already a balance check with the same eventId (avoid duplicate processing)
            checkDuplicateEvent(event);
            Balance balance = getSenderBalance(transactionDto);
            createAndSaveBalanceCheckLog(event, balance, transactionDto);
            updateBalance(balance, transactionDto);
            handleSuccess(event);
        } catch (Exception e) {
            handleBalanceCheckFail(event, e.getMessage());
            logger.error("There was an error while balance check", e);
        }

        kafkaProducer.sendEvent(jsonUtils.toJson(event));
    }

    private void checkDuplicateEvent(EventDTO event) {
        if (logRepository.existsByEventIdAndTransactionId(event.getEventId(), event.getTransactionId())) {
            throw new ValidationException(
                    String.format("There's another balance check for this eventId: %s and transactionId: %s",
                            event.getEventId(), event.getTransactionId()));
        }
    }

    private Balance getSenderBalance(TransactionDTO transactionDto) {
        return balanceRepository.findBySenderId(transactionDto.getUserId())
                .orElse(balanceRepository.save(Balance.builder()
                        .userId(transactionDto.getUserId())
                        .amount(BigDecimal.ZERO)
                        // TODO: add brl enum
                        .currency("BRL")
                        .build()
                ));
    }

    private void createAndSaveBalanceCheckLog(EventDTO event,
            Balance balance,
            TransactionDTO transactionDTO) {
        logRepository.save(
                BalanceCheckLog.builder()
                        .eventId(event.getEventId())
                        .transactionId(event.getTransactionId())
                        .balance(balance)
                        .previousValue(balance.getAmount())
                        .transactionValue(transactionDTO.getAmount())
                        .updatedValue(balance.getAmount().subtract(transactionDTO.getAmount()))
                        .build()
        );
    }

    private void updateBalance(Balance balance,
            TransactionDTO transactionDTO) {
        BigDecimal updatedValue = balance.getAmount().subtract(transactionDTO.getAmount());

        if (updatedValue.doubleValue() <= BigDecimal.ZERO.doubleValue()) {
            throw new ValidationException("You cannot perform this transaction because you don't have enough balance!");
        }
    }

    private void handleSuccess(EventDTO eventDTO) {
        eventDTO.setStatus(SagaStatus.SUCCESS);
        eventDTO.setSource(EventSource.BALANCE_SERVICE);
        addHistory(eventDTO, "Balance updated successfully!");
    }

    private void addHistory(EventDTO event,
            String message) {
        var history = HistoryDTO
                .builder()
                .source(event.getSource())
                .status(event.getStatus())
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
        event.addToHistory(history);
    }

    private void handleBalanceCheckFail(EventDTO eventDTO,
            String message) {
        eventDTO.setStatus(SagaStatus.ROLLBACK_PENDING);
        eventDTO.setSource(EventSource.BALANCE_SERVICE);
        addHistory(eventDTO, "Fail to update balance: ".concat(message));
    }

    public void rollbackBalance(EventDTO event) {
        event.setStatus(SagaStatus.FAIL);
        event.setSource(EventSource.BALANCE_SERVICE);

        // TODO: revert balance update
    }
}
