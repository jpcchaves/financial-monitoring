package com.financialmonitoring.transactionservice.adapter.input.rest.dto;

import java.io.Serial;
import java.io.Serializable;

public class TransactionTokenDTO implements Serializable {

    @Serial private static final long serialVersionUID = -1393976941773311156L;

    private String token;

    public TransactionTokenDTO() {}

    public TransactionTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public TransactionTokenDTO setToken(String token) {
        this.token = token;
        return this;
    }
}
