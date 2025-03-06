package com.financialmonitoring.userservice.core.repository;

import com.financialmonitoring.userservice.core.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(
            value = "SELECT * FROM tb_roles r WHERE UPPER(r.name) = UPPER(:name)",
            nativeQuery = true)
    Optional<Role> findByName(String name);
}
