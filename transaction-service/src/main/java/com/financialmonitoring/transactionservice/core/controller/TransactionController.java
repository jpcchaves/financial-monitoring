package com.financialmonitoring.transactionservice.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping
    public ResponseEntity<?> transfer() {
        logger.info("Received request to transfer.");
        return ResponseEntity.ok().build();
    }
}
