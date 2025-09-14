package com.financialmonitoring.transactionservice.core.service;

import com.financialmonitoring.transactionservice.infra.model.Event;
import com.financialmonitoring.transactionservice.infra.repository.jpa.EventJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventJpaRepository eventJpaRepository;

    public EventService(EventJpaRepository eventJpaRepository) {
        this.eventJpaRepository = eventJpaRepository;
    }

    public void notifyEnding(Event event) {
        // TODO
    }
}
