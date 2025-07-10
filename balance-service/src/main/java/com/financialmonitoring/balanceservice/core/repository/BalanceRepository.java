package com.financialmonitoring.balanceservice.core.repository;

import com.financialmonitoring.balanceservice.core.model.Balance;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, UUID> {

    @Query(value = "SELECT * FROM TB_BALANCE WHERE user_id = :senderId LIMIT 1", nativeQuery = true)
    Optional<Balance> findBySenderId(String senderId);
}
