package com.financialmonitoring.orchestrator.core;

import com.financialmonitoring.orchestrator.core.producer.KafkaProducer;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {

    private final KafkaProducer kafkaProducer;

    public TesteController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping
    public void sendTestEvent() {
        kafkaProducer.sendEvent("TESTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE " + LocalDateTime.now().getNano(), "teste-temporal");
    }
}
