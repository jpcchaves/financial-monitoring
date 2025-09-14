package com.financialmonitoring.userservice.infra.repository.jpa;

import com.financialmonitoring.userservice.infra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM tb_users u WHERE u.email = :email", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM tb_users u WHERE u.email = :email)", nativeQuery = true)
    Boolean existsByEmail(String email);
}
