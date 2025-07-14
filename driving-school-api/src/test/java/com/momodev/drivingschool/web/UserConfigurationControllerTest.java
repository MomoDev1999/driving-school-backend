package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.UserConfiguration;
import com.momodev.drivingschool.dto.UserConfigurationDto;
import com.momodev.drivingschool.service.UserConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserConfigurationControllerTest {

    @Mock
    private UserConfigurationService userConfigurationService;
    
    @Mock
    private Authentication authentication;
    
    private UserConfigurationController userConfigurationController;

    @BeforeEach
    void setUp() {
        userConfigurationController = new UserConfigurationController(userConfigurationService);
    }

    @Test
    void getUserConfiguration_returns_user_config() {
        // Arrange
        UserConfiguration userConfig = new UserConfiguration();
        userConfig.setId(1);
        userConfig.setDarkMode(true);
        
        when(userConfigurationService.getByAuthenticatedUser(authentication)).thenReturn(userConfig);

        // Act
        ResponseEntity<UserConfigurationDto> response = userConfigurationController.getUserConfiguration(authentication);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserConfigurationDto result = response.getBody();
        assertNotNull(result);
        assertTrue(result.isDarkMode());
    }

    @Test
    void updateDarkMode_returns_updated_config() {
        // Arrange
        UserConfiguration updatedConfig = new UserConfiguration();
        updatedConfig.setId(1);
        updatedConfig.setDarkMode(false);
        
        when(userConfigurationService.updateDarkMode(authentication, false)).thenReturn(updatedConfig);

        // Act
        ResponseEntity<UserConfigurationDto> response = userConfigurationController.updateDarkMode(false, authentication);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserConfigurationDto result = response.getBody();
        assertNotNull(result);
        assertFalse(result.isDarkMode());
    }
} 