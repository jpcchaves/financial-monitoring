package com.financialmonitoring.balanceservice.core.repository;

import com.financialmonitoring.balanceservice.core.model.BalanceCheckLog;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceCheckLogRepository extends JpaRepository<BalanceCheckLog, UUID> {

    boolean existsByEventIdAndTransactionId(String eventId,
            String transactionId);

    Optional<BalanceCheckLog> findByEventIdAndTransactionId(String eventId,
            String transactionId);
}
