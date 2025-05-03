package com.financialmonitoring.balanceservice.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TB_BALANCE")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false, unique = true)
    private Long userId;

    @Column(columnDefinition = "NUMERIC(19,4)")
    private BigDecimal amount;

    private String currency;

    public Balance() {
    }

    public Balance(UUID id, Long userId, BigDecimal amount, String currency) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Balance balance = (Balance) o;
        return Objects.equals(id, balance.id) && Objects.equals(userId, balance.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
