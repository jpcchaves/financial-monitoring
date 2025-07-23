package com.financialmonitoring.userservice.adapter.factory;

import com.financialmonitoring.userservice.adapter.dto.RegisterRequestDTO;
import com.financialmonitoring.userservice.adapter.out.entity.Role;
import com.financialmonitoring.userservice.adapter.out.entity.User;
import com.financialmonitoring.userservice.domain.port.factory.UserFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserFactoryImpl implements UserFactory {

    private final PasswordEncoder passwordEncoder;

    public UserFactoryImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUserFromDto(RegisterRequestDTO requestDTO, Set<Role> roles) {
        return User.builder()
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .roles(roles)
                .active(Boolean.TRUE)
                .build();
    }
}
