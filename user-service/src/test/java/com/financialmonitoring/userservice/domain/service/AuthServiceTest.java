package com.financialmonitoring.userservice.domain.service;

import com.financialmonitoring.userservice.adapter.dto.LoginRequestDTO;
import com.financialmonitoring.userservice.adapter.dto.LoginResponseDTO;
import com.financialmonitoring.userservice.adapter.dto.RegisterRequestDTO;
import com.financialmonitoring.userservice.adapter.dto.RegisterResponseDTO;
import com.financialmonitoring.userservice.adapter.out.entity.Role;
import com.financialmonitoring.userservice.adapter.out.entity.User;
import com.financialmonitoring.userservice.adapter.utils.TokenUtils;
import com.financialmonitoring.userservice.config.exception.BadRequestException;
import com.financialmonitoring.userservice.domain.port.factory.UserFactory;
import com.financialmonitoring.userservice.domain.port.out.AuthRepositoryPort;
import com.financialmonitoring.userservice.domain.port.out.RoleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private AuthRepositoryPort authRepositoryPort;

    @Mock
    private RoleRepositoryPort roleRepositoryPort;

    @Mock
    private final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(8);

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenUtils tokenUtils;

    @Mock
    private UserFactory userFactory;

    @InjectMocks
    private AuthService authService;

    private User user;
    private final String RAW_PASSWORD = "passwordtest123";
    private final String ENCODED_PASSWORD = PASSWORD_ENCODER.encode(RAW_PASSWORD);
    private final String FAKE_JWT_TOKEN = "sdafjsldjfojsd.sdafsdajfsdlajsad.fsadjflsadhfhsdafj";

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(123L)
                .email("test@test.com")
                .firstName("Test")
                .active(true)
                .password(ENCODED_PASSWORD)
                .roles(Set.of(new Role("ROLE_TEST")))
                .build();
    }

    @Test
    void shouldLoginSuccessfully_WhenCredentialsAreValid() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(user.getEmail(), RAW_PASSWORD);
        Authentication authenticationMock = mock(Authentication.class);

        when(authRepositoryPort.findByEmail(loginRequestDTO.getEmail())).thenReturn(Optional.of(user));
        when(PASSWORD_ENCODER.matches(loginRequestDTO.getPassword(), ENCODED_PASSWORD)).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authenticationMock);
        when(tokenUtils.generateToken(authenticationMock)).thenReturn(FAKE_JWT_TOKEN);

        LoginResponseDTO response = authService.login(loginRequestDTO);

        assertNotNull(response);
        assertEquals(FAKE_JWT_TOKEN, response.getToken());
        assertEquals(user.getEmail(), response.getUser()
                .getEmail());
    }

    @Test
    void shouldThrowBadRequestException_WhenPasswordIsInvalid() {
        String wrongPassword = RAW_PASSWORD + "wrong-password";
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(user.getEmail(), wrongPassword);

        when(authRepositoryPort.findByEmail(loginRequestDTO.getEmail())).thenReturn(Optional.of(user));
        when(PASSWORD_ENCODER.matches(wrongPassword, ENCODED_PASSWORD)).thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> authService.login(loginRequestDTO));

        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void shouldThrowBadRequestException_WhenAuthenticationFails() {
        when(authRepositoryPort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(PASSWORD_ENCODER.matches(RAW_PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        LoginRequestDTO request = new LoginRequestDTO(user.getEmail(), RAW_PASSWORD);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.login(request));

        assertTrue(exception.getMessage()
                .contains("Error authenticating user"));
    }

    @Test
    void shouldRegisterUserSuccessfully_WhenRegisterRequestIsValid() {
        Role roleTest = new Role(1L, "ROLE_TEST");
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setEmail(user.getEmail());
        registerRequestDTO.setPassword(RAW_PASSWORD);
        registerRequestDTO.setConfirmPassword(RAW_PASSWORD);

        when(authRepositoryPort.existsByEmail(user.getEmail())).thenReturn(false);
        when(authRepositoryPort.save(any(User.class))).thenReturn(user);
        when(roleRepositoryPort.getOrCreateDefaultRole()).thenReturn(roleTest);
        when(userFactory.createUserFromDto(registerRequestDTO, Set.of(roleTest))).thenReturn(user);

        RegisterResponseDTO response = authService.register(registerRequestDTO);

        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
    }

    @Test
    void shouldThrowException_WhenUserAlreadyExistsWithEmail() {
        when(authRepositoryPort.existsByEmail(user.getEmail())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.register(any()));

        assertTrue(exception.getMessage()
                .contains("Email already in use"));
    }

    @Test
    void shouldThrowException_WhenPasswordAndConfirmPasswordAreDifferent() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setEmail(user.getEmail());
        registerRequestDTO.setPassword(RAW_PASSWORD);
        registerRequestDTO.setConfirmPassword(RAW_PASSWORD + "different");

        when(authRepositoryPort.existsByEmail(user.getEmail())).thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> authService.register(registerRequestDTO));

        assertTrue(exception.getMessage()
                .contains("Passwords do not match"));
    }
}