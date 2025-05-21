package com.financialmonitoring.orchestrator.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financialmonitoring.commonlib.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private final ObjectMapper objectMapper;

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            logger.error("Error converting object to JSON", ex);
            return "";
        }
    }

    public EventDTO toEvent(String json) {
        try {
            return objectMapper.readValue(json, EventDTO.class);
        } catch (Exception ex) {
            logger.error("Error converting JSON to Event", ex);
            return null;
        }
    }
}
