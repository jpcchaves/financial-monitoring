package com.financialmonitoring.transactionservice.domain.service;

import com.financialmonitoring.transactionservice.infra.model.Event;
import com.financialmonitoring.transactionservice.port.input.EventUseCases;
import com.financialmonitoring.transactionservice.port.output.EventRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventService implements EventUseCases {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final EventRepositoryPort eventRepository;

    public EventService(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void notifyEnding(Event event) {
        logger.info("Received ending event");
        // TODO
    }
}
