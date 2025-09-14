package com.financialmonitoring.transactionservice.config.mapper;

import com.financialmonitoring.transactionservice.config.mapper.converter.mapper.TransactionConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    private final TransactionConverter transactionConverter;

    public MapperConfig(TransactionConverter transactionConverter) {
        this.transactionConverter = transactionConverter;
    }

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.addConverter(transactionConverter);
        return mapper;
    }
}
