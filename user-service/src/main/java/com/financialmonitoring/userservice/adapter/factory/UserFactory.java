package com.financialmonitoring.userservice.adapter.factory;

import com.financialmonitoring.userservice.adapter.dto.RegisterRequestDTO;
import com.financialmonitoring.userservice.infra.model.Role;
import com.financialmonitoring.userservice.infra.model.User;

import java.util.Set;

public interface UserFactory {

    User createUserFromDto(RegisterRequestDTO requestDTO, Set<Role> roles);
}
