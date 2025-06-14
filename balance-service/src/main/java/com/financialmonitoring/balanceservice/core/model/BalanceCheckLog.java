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
@Table(name = "BALANCE_CHECK_LOG_TB")
public class BalanceCheckLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    public UUID getId() {
        return id;
    }

    public BalanceCheckLog setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BalanceCheckLog setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Balance getBalance() {
        return balance;
    }

    public BalanceCheckLog setBalance(Balance balance) {
        this.balance = balance;
        return this;
    }

    public BigDecimal getTransactionValue() {
        return transactionValue;
    }

    public BalanceCheckLog setTransactionValue(BigDecimal transactionValue) {
        this.transactionValue = transactionValue;
        return this;
    }

    public BigDecimal getPreviousValue() {
        return previousValue;
    }

    public BalanceCheckLog setPreviousValue(BigDecimal previousValue) {
        this.previousValue = previousValue;
        return this;
    }

    public BigDecimal getUpdatedValue() {
        return updatedValue;
    }

    public BalanceCheckLog setUpdatedValue(BigDecimal updatedValue) {
        this.updatedValue = updatedValue;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BalanceCheckLog setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BalanceCheckLog setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
