package com.financialmonitoring.commonlib.dto;

import com.financialmonitoring.commonlib.enums.TransactionType;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6151677225070780497L;

    private String id;
    private String userId;
    private String receiverId;
    private String transactionEventId;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public TransactionDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TransactionDTO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public TransactionDTO setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public String getTransactionEventId() {
        return transactionEventId;
    }

    public TransactionDTO setTransactionEventId(String transactionEventId) {
        this.transactionEventId = transactionEventId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TransactionDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionDTO setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TransactionDTO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
