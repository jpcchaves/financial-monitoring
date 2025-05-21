package com.financialmonitoring.transactionservice.core.service;

import com.financialmonitoring.transactionservice.core.model.Event;
import com.financialmonitoring.transactionservice.core.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void notifyEnding(Event event) {
        // TODO
    }
}
