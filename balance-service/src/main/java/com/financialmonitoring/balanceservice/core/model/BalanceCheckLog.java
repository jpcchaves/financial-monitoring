package com.financialmonitoring.balanceservice.core.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TB_BALANCE_CHECK_LOG")
public class BalanceCheckLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String eventId;

    @Column(nullable = false, unique = true, length = 100)
    private String transactionId;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.REFRESH}, optional = false, targetEntity = Balance.class)
    @JoinColumn(name = "balance_id", nullable = false, foreignKey = @ForeignKey(name = "balance_fk", value = ConstraintMode.CONSTRAINT))
    private Balance balance;

    @Column(columnDefinition = "NUMERIC(19,4)", nullable = false)
    private BigDecimal transactionValue;

    @Column(columnDefinition = "NUMERIC(19,4)", nullable = false)
    private BigDecimal previousValue;

    @Column(columnDefinition = "NUMERIC(19,4)", nullable = false)
    private BigDecimal updatedValue;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public BalanceCheckLog(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.transactionId = builder.transactionId;
        this.balance = builder.balance;
        this.transactionValue = builder.transactionValue;
        this.previousValue = builder.previousValue;
        this.updatedValue = builder.updatedValue;
    }

    public BalanceCheckLog() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public BigDecimal getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(BigDecimal previousValue) {
        this.previousValue = previousValue;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public BigDecimal getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(BigDecimal transactionValue) {
        this.transactionValue = transactionValue;
    }

    public BigDecimal getUpdatedValue() {
        return updatedValue;
    }

    public void setUpdatedValue(BigDecimal updatedValue) {
        this.updatedValue = updatedValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class Builder {

        private UUID id;
        private String eventId;
        private String transactionId;
        private Balance balance;
        private BigDecimal transactionValue;
        private BigDecimal previousValue;
        private BigDecimal updatedValue;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder balance(Balance balance) {
            this.balance = balance;
            return this;
        }

        public Builder transactionValue(BigDecimal transactionValue) {
            this.transactionValue = transactionValue;
            return this;
        }

        public Builder previousValue(BigDecimal previousValue) {
            this.previousValue = previousValue;
            return this;
        }

        public Builder updatedValue(BigDecimal updatedValue) {
            this.updatedValue = updatedValue;
            return this;
        }

        public BalanceCheckLog build() {
            return new BalanceCheckLog(this);
        }
    }
}
