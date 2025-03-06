package com.financialmonitoring.userservice.core.repository;

import com.financialmonitoring.userservice.core.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM tb_users u WHERE u.email = :email", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(
            value = "SELECT EXISTS(SELECT 1 FROM tb_users u WHERE u.email = :email)",
            nativeQuery = true)
    Boolean existsByEmail(String email);
}
