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
import com.financialmonitoring.commonlib.enums.Currency;
import com.financialmonitoring.commonlib.enums.EventSource;
import com.financialmonitoring.commonlib.enums.SagaStatus;
import com.financialmonitoring.commonlib.exceptions.ResourceNotFoundException;
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
            TransactionDTO transactionDto = jsonUtils.toTransactionDto(event.getPayload());
            // verify if there's already a balance check with the same eventId (avoid duplicate processing)
            checkDuplicateEvent(event);

            // Get sender balance
            Balance senderBalance = getUserBalance(transactionDto.getUserId());
            validateSenderBalanceIsEnough(senderBalance.getAmount(), transactionDto.getAmount());

            // Get receiver balance
            Balance receiverBalance = getUserBalance(transactionDto.getReceiverId());

            // Save log
            createAndSaveBalanceCheckLog(event, senderBalance, transactionDto);
            createAndSaveBalanceCheckLog(event, receiverBalance, transactionDto);

            // Subtract sender balance or throw exception if balance is not enough
            subtractBalance(senderBalance, transactionDto);

            // Increase receiver balance
            increaseBalance(receiverBalance, transactionDto);

            // Send success event to orchestrator
            handleSuccess(event);
        } catch (Exception e) {
            handleBalanceCheckFail(event, e.getMessage());
            logger.error("There was an error while balance check", e);
        }

        kafkaProducer.sendEvent(jsonUtils.toJson(event));
    }

    private void validateSenderBalanceIsEnough(BigDecimal currentSenderBalance,
            BigDecimal transactionAmount) {
        if (currentSenderBalance.doubleValue() <= BigDecimal.ZERO.doubleValue()) {
            throw new ValidationException("You cannot perform this transaction because you don't have enough balance!");
        }

        if (transactionAmount.doubleValue() > currentSenderBalance.doubleValue()) {
            throw new ValidationException("You dont have enough balance!");
        }
    }

    private void checkDuplicateEvent(EventDTO event) {
        if (logRepository.existsByEventIdAndTransactionId(event.getEventId(), event.getTransactionId())) {
            throw new ValidationException(
                    String.format("There's another balance check for this eventId: %s and transactionId: %s",
                            event.getEventId(), event.getTransactionId()));
        }
    }

    private Balance getUserBalance(String userId) {
        return balanceRepository.findBySenderId(userId)
                .orElse(balanceRepository.save(Balance.builder()
                        .userId(userId)
                        .amount(BigDecimal.ZERO)
                        .currency(Currency.BRL.name())
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

    private void subtractBalance(Balance balance,
            TransactionDTO transactionDTO) {
        balance.setAmount(balance.getAmount().subtract(transactionDTO.getAmount()));
        balanceRepository.save(balance);
    }

    private void increaseBalance(Balance balance,
            TransactionDTO transactionDTO) {
        BigDecimal updatedValue = balance.getAmount().subtract(transactionDTO.getAmount());

        if (updatedValue.doubleValue() <= BigDecimal.ZERO.doubleValue()) {
            throw new ValidationException("You cannot perform this transaction because you don't have enough balance!");
        }

        balanceRepository.save(balance);
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
        try {
            event.setStatus(SagaStatus.FAIL);
            event.setSource(EventSource.BALANCE_SERVICE);

            TransactionDTO transactionDto = (TransactionDTO) event.getPayload();
            Balance balance = balanceRepository.findBySenderId(transactionDto.getUserId()).orElseThrow(
                    () -> new ResourceNotFoundException("There was an unexpected error trying to find user's balance"));

            BalanceCheckLog log = logRepository.findByEventIdAndTransactionId(event.getEventId(),
                            event.getTransactionId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format(
                            "There was an unexpected error trying to find the balance log of this transaction: %s from this event: %s",
                            event.getTransactionId(), event.getEventId())));

            balance.setAmount(log.getPreviousValue());
            balanceRepository.save(balance);

            addHistory(event, "Balance rolled back successfully!");
        } catch (Exception ex) {
            logger.error("");
            addHistory(event, "Rollback not executed for balance: ".concat(ex.getMessage()));
        }

        kafkaProducer.sendEvent(jsonUtils.toJson(event));
    }
}
