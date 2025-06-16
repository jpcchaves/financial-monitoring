package com.financialmonitoring.transactionservice.config.mapper;

import com.financialmonitoring.transactionservice.config.mapper.custom.TransactionMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    private final TransactionMapper transactionMapper;

    public MapperConfig(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.addConverter(transactionMapper);
        return mapper;
    }
}
