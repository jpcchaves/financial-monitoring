package com.financialmonitoring.commonlib.dto;

import java.time.LocalDateTime;

public class ExceptionResponseDTO {
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String details;

    public ExceptionResponseDTO() {}

    public ExceptionResponseDTO(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public ExceptionResponseDTO(Builder builder) {
        this.message = builder.message;
        this.details = builder.details;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public static class Builder {
        private String message;
        private String details;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withDetails(String details) {
            this.details = details;
            return this;
        }

        public ExceptionResponseDTO build() {
            return new ExceptionResponseDTO(this);
        }
    }
}
