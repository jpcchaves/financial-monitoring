package com.financialmonitoring.transactionservice.core.model;

import com.financialmonitoring.commonlib.enums.TransactionType;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = 2840935423246606740L;

    @Id
    private String id;
    private String userId;
    private String receiverId;
    private String transactionEventId;
    private String transactionToken;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private LocalDateTime createdAt;

    public Transaction() {
    }

    public Transaction(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.receiverId = builder.receiverId;
        this.transactionEventId = builder.transactionEventId;
        this.amount = builder.amount;
        this.description = builder.description;
        this.transactionType = builder.transactionType;
        this.createdAt = builder.createdAt;
        this.transactionToken = builder.transactionToken;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getTransactionEventId() {
        return transactionEventId;
    }

    public void setTransactionEventId(String transactionEventId) {
        this.transactionEventId = transactionEventId;
    }

    public String getTransactionToken() {
        return transactionToken;
    }

    public Transaction setTransactionToken(String transactionToken) {
        this.transactionToken = transactionToken;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(transactionEventId,
                that.transactionEventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionEventId);
    }

    @Override
    public String toString() {
        return "TransactionPayload{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", transactionEventId='" + transactionEventId + '\'' +
                '}';
    }

    public static class Builder {
        private String id;
        private String userId;
        private String receiverId;
        private String transactionEventId;
        private String transactionToken;
        private BigDecimal amount;
        private String description;
        private TransactionType transactionType;
        private LocalDateTime createdAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder receiverId(String receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public Builder transactionEventId(String transactionEventId) {
            this.transactionEventId = transactionEventId;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
