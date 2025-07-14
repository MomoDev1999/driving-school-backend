package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.UserConfiguration;
import com.momodev.drivingschool.dto.UserConfigurationDto;
import com.momodev.drivingschool.service.UserConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class UserConfigurationController {

    private final UserConfigurationService service;

    public UserConfigurationController(UserConfigurationService service) {
        this.service = service;
    }

    /**
     * Devuelve la configuración del usuario autenticado (modo oscuro).
     */
    @GetMapping
    public ResponseEntity<UserConfigurationDto> getUserConfiguration(Authentication authentication) {
        UserConfiguration config = service.getByAuthenticatedUser(authentication);
        return ResponseEntity.ok(new UserConfigurationDto(config.getDarkMode()));
    }

    /**
     * Actualiza el modo oscuro para el usuario autenticado.
     */
    @PatchMapping("/dark-mode")
    public ResponseEntity<UserConfigurationDto> updateDarkMode(
            @RequestParam boolean enabled,
            Authentication authentication) {
        UserConfiguration updated = service.updateDarkMode(authentication, enabled);
        return ResponseEntity.ok(new UserConfigurationDto(updated.getDarkMode()));
    }
}
