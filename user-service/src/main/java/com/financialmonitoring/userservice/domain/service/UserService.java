package com.financialmonitoring.userservice.domain.service;

import com.financialmonitoring.userservice.port.output.AuthRepositoryPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {

    private final AuthRepositoryPort authRepositoryPort;

    public UserService(AuthRepositoryPort authRepositoryPort) {
        this.authRepositoryPort = authRepositoryPort;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authRepositoryPort.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with the given email: " + email));
    }
}
