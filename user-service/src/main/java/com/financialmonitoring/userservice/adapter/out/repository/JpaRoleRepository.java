package com.financialmonitoring.userservice.adapter.out.repository;

import com.financialmonitoring.userservice.infra.model.Role;
import com.financialmonitoring.userservice.domain.port.out.RoleRepositoryPort;
import com.financialmonitoring.userservice.infra.repository.jpa.SpringDataRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaRoleRepository implements RoleRepositoryPort {

    private final SpringDataRoleRepository repository;

    public JpaRoleRepository(SpringDataRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Role saveAndFlush(Role role) {
        return repository.saveAndFlush(role);
    }

    @Override
    public Role getOrCreateDefaultRole() {
        return repository.findByName("ROLE_USER")
                .orElseGet(() -> repository.saveAndFlush(new Role("ROLE_USER")));
    }
}
