package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserConfiguration;
import com.momodev.drivingschool.repository.UserConfigurationRepository;
import com.momodev.drivingschool.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserConfigurationService {

    private final UserConfigurationRepository repo;
    private final UserRepository userRepo;

    public UserConfigurationService(UserConfigurationRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    /**
     * Obtiene la configuración de un usuario por ID (versión original).
     */
    public UserConfiguration getConfiguration(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return repo.findByUser(user)
                .orElseGet(() -> {
                    UserConfiguration cfg = UserConfiguration.builder()
                            .user(user)
                            .darkMode(false)
                            .build();
                    return repo.save(cfg);
                });
    }

    /**
     * Actualiza modo oscuro por ID de usuario.
     */
    public UserConfiguration updateDarkMode(Integer userId, boolean darkMode) {
        UserConfiguration cfg = getConfiguration(userId);
        cfg.setDarkMode(darkMode);
        return repo.save(cfg);
    }

    /**
     * Obtiene la configuración del usuario autenticado.
     */
    public UserConfiguration getByAuthenticatedUser(Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + username));

        return repo.findByUser(user)
                .orElseGet(() -> {
                    UserConfiguration cfg = UserConfiguration.builder()
                            .user(user)
                            .darkMode(false)
                            .build();
                    return repo.save(cfg);
                });
    }

    /**
     * Actualiza modo oscuro para el usuario autenticado.
     */
    public UserConfiguration updateDarkMode(Authentication auth, boolean darkMode) {
        UserConfiguration cfg = getByAuthenticatedUser(auth);
        cfg.setDarkMode(darkMode);
        return repo.save(cfg);
    }
}
