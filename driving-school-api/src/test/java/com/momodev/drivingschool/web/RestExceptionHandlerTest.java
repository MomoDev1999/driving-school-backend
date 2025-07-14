package com.momodev.drivingschool.web;

import com.momodev.drivingschool.util.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void setUp() {
        restExceptionHandler = new RestExceptionHandler();
    }

    @Test
    void handleApiException_returns_expected_response() {
        // Arrange
        ApiException exception = new ApiException("Mensaje de error de prueba");

        // Act
        ResponseEntity<Map<String, String>> response = restExceptionHandler.handleApi(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals("Mensaje de error de prueba", body.get("error"));
    }
} 