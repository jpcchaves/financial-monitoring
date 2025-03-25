package com.financialmonitoring.commonlib.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class HistoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1926593896827655282L;

    private String source;
    private String message;
    private LocalDateTime createdAt;

    public HistoryDTO(Builder builder) {
        this.source = builder.source;
        this.message = builder.message;
        this.createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

        private String source;
        private String message;
        private LocalDateTime createdAt;

        public Builder source(String source) {
            this.source = source;
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
