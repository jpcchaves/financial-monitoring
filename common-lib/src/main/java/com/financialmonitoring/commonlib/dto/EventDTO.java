package com.financialmonitoring.commonlib.dto;

import com.financialmonitoring.commonlib.enums.EventSource;
import com.financialmonitoring.commonlib.enums.SagaStatus;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7389224274356745896L;
    
    private String id;
    private String eventId;
    private EventSource source;
    private SagaStatus status;
    private Object payload;
    private List<HistoryDTO> eventHistory;
    private LocalDateTime createdAt;

    public EventDTO() {
    }

    public EventDTO(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.source = builder.source;
        this.status = builder.status;
        this.payload = builder.payload;
        this.eventHistory = builder.eventHistory;
        this.createdAt = builder.createdAt;
    }

    public EventDTO(String id,
            String eventId,
            EventSource source,
            SagaStatus status,
            Object payload,
            List<HistoryDTO> eventHistory,
            LocalDateTime createdAt) {
        this.id = id;
        this.eventId = eventId;
        this.source = source;
        this.status = status;
        this.payload = payload;
        this.eventHistory = eventHistory;
        this.createdAt = createdAt;
    }

    public void addToHistory(HistoryDTO history) {
        if (eventHistory == null || eventHistory.isEmpty()) {
            eventHistory = new ArrayList<>();
        }
        eventHistory.add(history);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public EventSource getSource() {
        return source;
    }

    public void setSource(EventSource source) {
        this.source = source;
    }

    public SagaStatus getStatus() {
        return status;
    }

    public void setStatus(SagaStatus status) {
        this.status = status;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public List<HistoryDTO> getEventHistory() {
        return eventHistory;
    }

    public void setEventHistory(List<HistoryDTO> eventHistory) {
        this.eventHistory = eventHistory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class Builder {

        private String id;
        private String eventId;
        private EventSource source;
        private SagaStatus status;
        private Object payload;
        private List<HistoryDTO> eventHistory;
        private LocalDateTime createdAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder source(EventSource source) {
            this.source = source;
            return this;
        }

        public Builder status(SagaStatus status) {
            this.status = status;
            return this;
        }

        public Builder payload(Object payload) {
            this.payload = payload;
            return this;
        }

        public Builder eventHistory(List<HistoryDTO> eventHistory) {
            this.eventHistory = eventHistory;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EventDTO build() {
            return new EventDTO(this);
        }

    }
}
