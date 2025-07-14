package com.momodev.drivingschool.service;

import com.momodev.drivingschool.config.JwtTokenProvider;
import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.dto.AuthRequest;
import com.momodev.drivingschool.dto.AuthResponse;
import com.momodev.drivingschool.dto.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthenticationManager authManager;
    private JwtTokenProvider jwt;
    private UserService users;
    private AuthService service;

    @BeforeEach
    void setUp() {
        authManager = mock(AuthenticationManager.class);
        jwt = mock(JwtTokenProvider.class);
        users = mock(UserService.class);
        service = new AuthService(authManager, jwt, users);
    }

    @Test
    void login_returnsToken() {
        AuthRequest req = new AuthRequest("user", "pass");
        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(jwt.generate("user")).thenReturn("mocked-token");

        AuthResponse resp = service.login(req);

        assertThat(resp.token()).isEqualTo("mocked-token");
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void register_savesUser_andReturnsToken() {
        RegisterRequest req = new RegisterRequest("user", "name", "mail@mail.com", "pass", null);
        User user = User.builder().username("user").build();
        when(users.save(any())).thenReturn(user);
        when(jwt.generate("user")).thenReturn("token2");

        AuthResponse resp = service.register(req);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(users).save(captor.capture());
        assertThat(captor.getValue().getUsername()).isEqualTo("user");
        assertThat(resp.token()).isEqualTo("token2");
    }
}
