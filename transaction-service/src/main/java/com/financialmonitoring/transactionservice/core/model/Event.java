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
    private String transactionId;
    private String source;
    private SagaStatus sagaStatus;
    private Object payload;
    private List<HistoryDTO> eventHistory;
    private LocalDateTime createdAt;

    public Event() {
    }

    public Event(String id,
            String transactionId,
            String source,
            SagaStatus sagaStatus,
            Object payload,
            List<HistoryDTO> eventHistory,
            LocalDateTime createdAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.source = source;
        this.sagaStatus = sagaStatus;
        this.payload = payload;
        this.eventHistory = eventHistory;
        this.createdAt = createdAt;
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

    public SagaStatus getSagaStatus() {
        return sagaStatus;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(transactionId, event.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionId);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", source='" + source + '\'' +
                ", sagaStatus=" + sagaStatus +
                ", payload=" + payload +
                ", eventHistory=" + eventHistory +
                ", createdAt=" + createdAt +
                '}';
    }
}
