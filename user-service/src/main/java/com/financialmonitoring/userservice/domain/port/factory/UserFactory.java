package com.financialmonitoring.userservice.domain.port.factory;

import com.financialmonitoring.userservice.adapter.dto.RegisterRequestDTO;
import com.financialmonitoring.userservice.adapter.out.entity.Role;
import com.financialmonitoring.userservice.adapter.out.entity.User;

import java.util.Set;

public interface UserFactory {

    User createUserFromDto(RegisterRequestDTO requestDTO, Set<Role> roles);
}
