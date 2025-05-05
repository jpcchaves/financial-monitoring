package com.financialmonitoring.transactionservice.config.mapper.custom;

import com.financialmonitoring.transactionservice.core.dto.TransactionDTO;
import com.financialmonitoring.transactionservice.core.model.Transaction;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionMapper implements Converter<TransactionDTO, Transaction> {

    @Override
    public Transaction convert(MappingContext<TransactionDTO, Transaction> mappingContext) {
        TransactionDTO transactionDTO = mappingContext.getSource();

        return Transaction.builder()
                .userId(transactionDTO.getUserId())
                .receiverId(transactionDTO.getReceiverId())
                .amount(transactionDTO.getAmount())
                .description(transactionDTO.getDescription())
                .transactionType(transactionDTO.getTransactionType())
                .build();
    }
}
