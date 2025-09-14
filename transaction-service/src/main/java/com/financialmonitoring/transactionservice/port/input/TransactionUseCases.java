package com.financialmonitoring.transactionservice.port.input;

import com.financialmonitoring.transactionservice.adapter.input.rest.dto.TransactionRequestDTO;
import com.financialmonitoring.transactionservice.adapter.input.rest.dto.TransactionTokenDTO;

public interface TransactionUseCases {
    TransactionRequestDTO doTransfer(TransactionRequestDTO requestDTO);
    TransactionTokenDTO generateTransactionToken();
}
