package com.financialmonitoring.balanceservice.core.service;

import com.financialmonitoring.balanceservice.core.repository.BalanceCheckLogRepository;
import com.financialmonitoring.balanceservice.core.repository.BalanceRepository;
import com.financialmonitoring.commonlib.dto.EventDTO;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final BalanceCheckLogRepository logRepository;

    public BalanceService(BalanceRepository balanceRepository,
            BalanceCheckLogRepository logRepository) {
        this.balanceRepository = balanceRepository;
        this.logRepository = logRepository;
    }

    public void doBalanceCheck(EventDTO event) {
        // TODO: implement balance check
    }

    public void rollbackBalance(EventDTO event) {
        // TODO: implement rollback
    }
}
