package com.financialmonitoring.transactionservice.core.model;

import com.financialmonitoring.commonlib.dto.HistoryDTO;
import com.financialmonitoring.commonlib.enums.SagaStatus;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Event implements Serializable {

    @Serial
    private static final long serialVersionUID = -5906042640604343912L;

    @Id
    private String id;
    private String eventId;
    private String source;
    private SagaStatus sagaStatus;
    private String payload;
    private List<HistoryDTO> eventHistory;
    private LocalDateTime createdAt;

    public Event() {
    }

    private Event(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.source = builder.source;
        this.sagaStatus = builder.sagaStatus;
        this.payload = builder.payload;
        this.eventHistory = builder.eventHistory;
        this.createdAt = builder.createdAt;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public SagaStatus getSagaStatus() {
        return sagaStatus;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", eventId='" + eventId + '\'' +
                ", source='" + source + '\'' +
                ", sagaStatus=" + sagaStatus +
                ", payload=" + payload +
                ", eventHistory=" + eventHistory +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class Builder {
        private String id;
        private String eventId;
        private String source;
        private SagaStatus sagaStatus;
        private String payload;
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

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder status(SagaStatus sagaStatus) {
            this.sagaStatus = sagaStatus;
            return this;
        }

        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        public Builder history(List<HistoryDTO> eventHistory) {
            this.eventHistory = eventHistory;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }
}
