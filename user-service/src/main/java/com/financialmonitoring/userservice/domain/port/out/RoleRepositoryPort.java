package com.financialmonitoring.userservice.domain.port.out;


import com.financialmonitoring.userservice.infra.model.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepositoryPort {

    @Query(value = "SELECT * FROM tb_roles r WHERE UPPER(r.name) = UPPER(:name)", nativeQuery = true)
    Optional<Role> findByName(String name);

    Role saveAndFlush(Role role);

    Role getOrCreateDefaultRole();
}
