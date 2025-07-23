package com.financialmonitoring.userservice.adapter.out;

import com.financialmonitoring.userservice.adapter.out.entity.Role;
import com.financialmonitoring.userservice.adapter.out.repository.SpringDataRoleRepository;
import com.financialmonitoring.userservice.domain.port.out.RoleRepositoryPort;
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
