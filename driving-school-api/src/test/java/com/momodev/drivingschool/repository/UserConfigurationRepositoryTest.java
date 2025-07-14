package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserConfigurationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserConfigurationRepository userConfigurationRepository;

    @Test
    void save_userConfiguration_returns_saved_configuration() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = entityManager.persistAndFlush(user);

        UserConfiguration config = new UserConfiguration();
        config.setUser(savedUser);
        config.setDarkMode(true);

        // When
        UserConfiguration saved = userConfigurationRepository.save(config);

        // Then
        assertNotNull(saved.getId());
        assertEquals(savedUser.getId(), saved.getUser().getId());
        assertTrue(saved.getDarkMode());
    }

    @Test
    void findByUser_returns_configuration_when_exists() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = entityManager.persistAndFlush(user);

        UserConfiguration config = new UserConfiguration();
        config.setUser(savedUser);
        config.setDarkMode(false);
        entityManager.persistAndFlush(config);

        // When
        Optional<UserConfiguration> found = userConfigurationRepository.findByUser(savedUser);

        // Then
        assertTrue(found.isPresent());
        assertEquals(savedUser.getId(), found.get().getUser().getId());
        assertFalse(found.get().getDarkMode());
    }

    @Test
    void findByUser_returns_empty_when_not_exists() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = entityManager.persistAndFlush(user);

        // When
        Optional<UserConfiguration> found = userConfigurationRepository.findByUser(savedUser);

        // Then
        assertFalse(found.isPresent());
    }
} 