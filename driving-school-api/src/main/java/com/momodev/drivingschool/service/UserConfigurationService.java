package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserConfiguration;
import com.momodev.drivingschool.repository.UserConfigurationRepository;
import com.momodev.drivingschool.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserConfigurationService {

    private final UserConfigurationRepository repo;
    private final UserRepository userRepo;

    public UserConfigurationService(UserConfigurationRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

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

    public UserConfiguration updateDarkMode(Integer userId, boolean darkMode) {
        UserConfiguration cfg = getConfiguration(userId);
        cfg.setDarkMode(darkMode);
        return repo.save(cfg);
    }
}
