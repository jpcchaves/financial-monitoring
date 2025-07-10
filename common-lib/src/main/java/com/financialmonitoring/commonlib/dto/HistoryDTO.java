package com.financialmonitoring.commonlib.dto;

import com.financialmonitoring.commonlib.enums.EventSource;
import com.financialmonitoring.commonlib.enums.SagaStatus;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class HistoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1926593896827655282L;

    private EventSource source;
    private SagaStatus status;
    private String message;
    private LocalDateTime createdAt;

    public HistoryDTO() {
    }

    public HistoryDTO(Builder builder) {
        this.source = builder.source;
        this.status = builder.status;
        this.message = builder.message;
        this.createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class Builder {

        private EventSource source;
        private SagaStatus status;
        private String message;
        private LocalDateTime createdAt;

        public Builder source(EventSource source) {
            this.source = source;
            return this;
        }

        public Builder status(SagaStatus status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HistoryDTO build() {
            return new HistoryDTO(this);
        }
    }
}
