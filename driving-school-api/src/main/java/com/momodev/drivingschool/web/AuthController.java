package com.momodev.drivingschool.web;

import com.momodev.drivingschool.dto.*;
import com.momodev.drivingschool.service.AuthService;
import com.momodev.drivingschool.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;
    private final UserService users;

    public AuthController(AuthService auth, UserService users) {
        this.auth = auth;
        this.users = users;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
        AuthResponse resp = auth.login(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        AuthResponse resp = auth.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal com.momodev.drivingschool.domain.User user,
            @Valid @RequestBody PasswordChangeRequest req) {

        users.changePassword(user, req.oldPassword(), req.newPassword());
        return ResponseEntity.noContent().build();
    }
}
