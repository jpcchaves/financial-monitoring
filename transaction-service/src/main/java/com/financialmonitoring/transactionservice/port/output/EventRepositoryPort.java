package com.financialmonitoring.transactionservice.port.output;

import com.financialmonitoring.transactionservice.infra.model.Event;

public interface EventRepositoryPort {
    Event save(Event event);
}
