package com.financialmonitoring.userservice.domain.service;

import com.financialmonitoring.userservice.infra.model.Role;
import com.financialmonitoring.userservice.infra.model.User;
import com.financialmonitoring.userservice.domain.port.out.AuthRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AuthRepositoryPort authRepositoryPort;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void beforeEach() {
        user = User.builder().id(123L).email("test@test.com").firstName("Test").active(true).roles(
                Set.of(new Role("ROLE_TEST"))).build();
    }

    @Test
    void shouldGetUserByEmail_whenUserExistsByEmail() {
        // mock method behaviour
        when(authRepositoryPort.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));

        User foundUser = (User) userService.loadUserByUsername(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getFirstName(), foundUser.getFirstName());
        assertEquals(user.getRoles(), foundUser.getRoles());
    }

    @Test
    void shouldThrowUsernameNotFoundException_whenUserDontExistsByEmail() {
        // mock method behaviour
        when(authRepositoryPort.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(user.getEmail()));

        assertEquals(String.format("User not found with the given email: %s", user.getEmail()), result.getMessage());
    }
}