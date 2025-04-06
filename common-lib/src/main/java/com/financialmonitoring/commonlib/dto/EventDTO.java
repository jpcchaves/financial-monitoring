package com.financialmonitoring.commonlib.dto;

import com.financialmonitoring.commonlib.enums.SagaStatus;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class EventDTO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7389224274356745896L;
    
    private String id;
    private String transactionId;
    private String source;
    private SagaStatus status;
    private T payload;
    private List<HistoryDTO> eventHistory;
    private LocalDateTime createdAt;

    public EventDTO(Builder<T> builder) {
        this.id = builder.id;
        this.transactionId = builder.transactionId;
        this.source = builder.source;
        this.status = builder.status;
        this.payload = builder.payload;
        this.eventHistory = builder.eventHistory;
        this.createdAt = builder.createdAt;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public SagaStatus getStatus() {
        return status;
    }

    public void setStatus(SagaStatus status) {
        this.status = status;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
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

    public static class Builder<T> {

        private String id;
        private String transactionId;
        private String source;
        private SagaStatus status;
        private T payload;
        private List<HistoryDTO> eventHistory;
        private LocalDateTime createdAt;

        public Builder<T> id(String id) {
            this.id = id;
            return this;
        }

        public Builder<T> transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder<T> source(String source) {
            this.source = source;
            return this;
        }

        public Builder<T> status(SagaStatus status) {
            this.status = status;
            return this;
        }

        public Builder<T> payload(T payload) {
            this.payload = payload;
            return this;
        }

        public Builder<T> eventHistory(List<HistoryDTO> eventHistory) {
            this.eventHistory = eventHistory;
            return this;
        }

        public Builder<T> createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EventDTO<T> build() {
            return new EventDTO<>(this);
        }

    }
}
