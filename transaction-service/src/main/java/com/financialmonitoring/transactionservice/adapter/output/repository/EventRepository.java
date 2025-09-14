package com.financialmonitoring.transactionservice.adapter.output.repository;

import com.financialmonitoring.transactionservice.infra.model.Event;
import com.financialmonitoring.transactionservice.infra.repository.jpa.EventJpaRepository;
import com.financialmonitoring.transactionservice.port.output.EventRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepository implements EventRepositoryPort {

    private final EventJpaRepository eventJpaRepository;

    public EventRepository(EventJpaRepository eventJpaRepository) {
        this.eventJpaRepository = eventJpaRepository;
    }

    @Override
    public Event save(Event event) {
        return eventJpaRepository.save(event);
    }
}
