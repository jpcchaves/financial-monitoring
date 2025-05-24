package com.financialmonitoring.transactionservice.config.mapper.custom;

import com.financialmonitoring.transactionservice.core.dto.TransactionRequestDTO;
import com.financialmonitoring.transactionservice.core.model.Transaction;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionMapper implements Converter<TransactionRequestDTO, Transaction> {

    @Override
    public Transaction convert(MappingContext<TransactionRequestDTO, Transaction> mappingContext) {
        TransactionRequestDTO transactionRequestDTO = mappingContext.getSource();

        return Transaction.builder()
                .userId(transactionRequestDTO.getUserId())
                .receiverId(transactionRequestDTO.getReceiverId())
                .amount(transactionRequestDTO.getAmount())
                .description(transactionRequestDTO.getDescription())
                .transactionType(transactionRequestDTO.getTransactionType())
                .build();
    }
}
