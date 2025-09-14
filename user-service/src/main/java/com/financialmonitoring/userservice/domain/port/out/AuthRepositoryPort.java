package com.financialmonitoring.userservice.domain.port.out;

import com.financialmonitoring.userservice.infra.model.User;

import java.util.Optional;

public interface AuthRepositoryPort {
    User save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
