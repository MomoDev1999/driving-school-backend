package com.momodev.drivingschool.service;

import com.momodev.drivingschool.config.JwtTokenProvider;
import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.dto.AuthRequest;
import com.momodev.drivingschool.dto.AuthResponse;
import com.momodev.drivingschool.dto.RegisterRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwt;
    private final UserService users;

    public AuthService(AuthenticationManager authManager,
            JwtTokenProvider jwt,
            UserService users) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.users = users;
    }

    public AuthResponse login(AuthRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        String token = jwt.generate(auth.getName());
        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest r) {
        User nuevo = User.builder()
                .username(r.username())
                .name(r.name())
                .email(r.email())
                .password(r.password())
                .birthday(r.birthday())
                .build();
        User guardado = users.save(nuevo);
        String token = jwt.generate(guardado.getUsername());
        return new AuthResponse(token);
    }
}
