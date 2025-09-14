package com.financialmonitoring.transactionservice.adapter.input.rest;

import com.financialmonitoring.transactionservice.adapter.input.rest.dto.TransactionRequestDTO;
import com.financialmonitoring.transactionservice.adapter.input.rest.dto.TransactionTokenDTO;
import com.financialmonitoring.transactionservice.domain.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<TransactionRequestDTO> transfer(@Valid @RequestBody TransactionRequestDTO requestBody) {
        return ResponseEntity.ok(transactionService.doTransfer(requestBody));
    }

    @GetMapping("/generate-token")
    public ResponseEntity<TransactionTokenDTO> getTransactionToken() {
        return ResponseEntity.ok(transactionService.generateTransactionToken());
    }
}
