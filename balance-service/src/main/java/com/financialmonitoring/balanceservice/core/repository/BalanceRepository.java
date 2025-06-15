package com.financialmonitoring.balanceservice.core.repository;

import com.financialmonitoring.balanceservice.core.model.Balance;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, UUID> {

}
