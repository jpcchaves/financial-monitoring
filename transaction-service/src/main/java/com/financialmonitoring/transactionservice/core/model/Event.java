package com.financialmonitoring.transactionservice.core.model;

import com.financialmonitoring.commonlib.dto.EventDTO;
import com.financialmonitoring.commonlib.dto.HistoryDTO;
import com.financialmonitoring.commonlib.enums.SagaStatus;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Event extends EventDTO<Object> {

    @Serial
    private static final long serialVersionUID = -8089340941736848444L;

    public Event(Builder<Object> builder) {
        super(builder);
    }

    public Event(String id, String transactionId, String source, SagaStatus status,
            Object payload, List<HistoryDTO> eventHistory,
            LocalDateTime createdAt) {
        super(id, transactionId, source, status, payload, eventHistory, createdAt);
    }

    @Override
    @Id
    public String getId() {
        return super.getId();
    }
}
