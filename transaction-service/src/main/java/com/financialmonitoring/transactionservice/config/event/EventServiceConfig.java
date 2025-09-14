package com.financialmonitoring.transactionservice.config.event;

import com.financialmonitoring.transactionservice.domain.service.EventService;
import com.financialmonitoring.transactionservice.port.output.EventRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventServiceConfig {

    @Bean
    public EventService eventService(EventRepositoryPort eventRepository) {
        return new EventService(eventRepository);
    }
}
