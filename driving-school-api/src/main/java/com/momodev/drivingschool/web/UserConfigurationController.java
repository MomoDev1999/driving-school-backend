package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserConfiguration;
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

    @GetMapping
    public ResponseEntity<UserConfiguration> getConfig(Authentication auth) {
        Integer userId = ((User) auth.getPrincipal()).getId();
        return ResponseEntity.ok(service.getConfiguration(userId));
    }

    @PatchMapping("/dark-mode")
    public ResponseEntity<UserConfiguration> setDarkMode(
            Authentication auth,
            @RequestParam boolean enabled) {
        Integer userId = ((User) auth.getPrincipal()).getId();
        return ResponseEntity.ok(service.updateDarkMode(userId, enabled));
    }
}
