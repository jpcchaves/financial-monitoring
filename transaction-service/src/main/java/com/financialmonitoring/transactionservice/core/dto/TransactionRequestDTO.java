package com.financialmonitoring.transactionservice.core.dto;

import com.financialmonitoring.commonlib.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class TransactionRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3366598129423135657L;

    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotBlank(message = "Receiver ID cannot be blank")
    private String receiverId;

    @Positive(message = "Amount must be positive")
    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    private String description;

    @NotNull(message = "Transaction type cannot be null")
    private TransactionType transactionType;

    @NotBlank(message = "The request must contain a transaction token to proceed")
    private String transactionToken;

    public TransactionRequestDTO() {
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

    public String getTransactionToken() {
        return transactionToken;
    }

    public void setTransactionToken(String transactionToken) {
        this.transactionToken = transactionToken;
    }
}
