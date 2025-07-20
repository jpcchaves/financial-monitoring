package com.financialmonitoring.userservice.adapter.out.repository;

import com.financialmonitoring.userservice.adapter.out.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataRoleRepository extends JpaRepository<Role, Long> {

    @Query(
            value = "SELECT * FROM tb_roles r WHERE UPPER(r.name) = UPPER(:name)",
            nativeQuery = true)
    Optional<Role> findByName(String name);
}
