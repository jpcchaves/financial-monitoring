package com.financialmonitoring.transactionservice.core.controller;

import com.financialmonitoring.transactionservice.core.dto.TransactionDTO;
import com.financialmonitoring.transactionservice.core.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> transfer(@Valid @RequestBody TransactionDTO requestBody) {
        return ResponseEntity.ok(transactionService.doTransfer(requestBody));
    }
}
