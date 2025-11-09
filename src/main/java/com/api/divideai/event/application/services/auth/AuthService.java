package com.api.divideai.event.application.services.auth;

import com.api.divideai.event.domain.collections.User;
import com.api.divideai.event.domain.dtos.auth.LoginRequestDTO;
import com.api.divideai.event.domain.dtos.auth.LoginResponseDTO;
import com.api.divideai.event.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtService.generateToken(user.getId());

        return new LoginResponseDTO(token, user.getId());
    }
}

