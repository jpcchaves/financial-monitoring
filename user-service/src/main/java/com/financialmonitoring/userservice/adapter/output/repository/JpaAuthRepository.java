package com.financialmonitoring.userservice.adapter.output.repository;

import com.financialmonitoring.userservice.infra.model.User;
import com.financialmonitoring.userservice.port.output.AuthRepositoryPort;
import com.financialmonitoring.userservice.infra.repository.jpa.SpringDataUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaAuthRepository implements AuthRepositoryPort {

    private final SpringDataUserRepository userRepository;

    public JpaAuthRepository(SpringDataUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
