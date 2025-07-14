package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.dto.AuthRequest;
import com.momodev.drivingschool.dto.AuthResponse;
import com.momodev.drivingschool.dto.PasswordChangeRequest;
import com.momodev.drivingschool.dto.RegisterRequest;
import com.momodev.drivingschool.service.AuthService;
import com.momodev.drivingschool.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;
    
    @Mock
    private UserService userService;
    
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authService, userService);
    }

    @Test
    void login_returns_auth_response() {
        // Arrange
        AuthRequest request = new AuthRequest("testuser", "password123");
        AuthResponse expectedResponse = new AuthResponse("dummy-token-123");
        
        when(authService.login(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<AuthResponse> response = authController.login(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        AuthResponse result = response.getBody();
        assertNotNull(result);
        assertEquals("dummy-token-123", result.token());
    }

    @Test
    void register_returns_auth_response_with_created_status() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
            "testuser", "Test User", "test@example.com", "password123", 
            LocalDate.of(1990, 1, 1)
        );
        AuthResponse expectedResponse = new AuthResponse("dummy-token-456");
        
        when(authService.register(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<AuthResponse> response = authController.register(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        AuthResponse result = response.getBody();
        assertNotNull(result);
        assertEquals("dummy-token-456", result.token());
    }

    @Test
    void changePassword_returns_no_content() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        
        PasswordChangeRequest request = new PasswordChangeRequest("oldPassword", "newPassword");
        
        doNothing().when(userService).changePassword(user, "oldPassword", "newPassword");

        // Act
        ResponseEntity<Void> response = authController.changePassword(user, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userService).changePassword(user, "oldPassword", "newPassword");
    }
}
