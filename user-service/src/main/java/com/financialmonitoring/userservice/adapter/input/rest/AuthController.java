package com.financialmonitoring.userservice.adapter.input.rest;

import com.financialmonitoring.userservice.adapter.dto.LoginRequestDTO;
import com.financialmonitoring.userservice.adapter.dto.LoginResponseDTO;
import com.financialmonitoring.userservice.adapter.dto.RegisterRequestDTO;
import com.financialmonitoring.userservice.adapter.dto.RegisterResponseDTO;
import com.financialmonitoring.userservice.port.input.LoginUseCase;
import com.financialmonitoring.userservice.port.input.RegisterUserUseCase;
import com.financialmonitoring.userservice.port.input.VerifyTokenUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final VerifyTokenUseCase verifyTokenUseCase;

    public AuthController(LoginUseCase loginUseCase, RegisterUserUseCase registerUserUseCase, VerifyTokenUseCase verifyTokenUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.verifyTokenUseCase = verifyTokenUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(loginUseCase.login(requestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        return ResponseEntity.ok(registerUserUseCase.register(requestDTO));
    }

    @GetMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        return verifyTokenUseCase.verifyToken(authHeader)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
