package com.financialmonitoring.transactionservice.port.input;

import com.financialmonitoring.transactionservice.infra.model.Event;

public interface EventUseCases {

    void notifyEnding(Event event);
}
