package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_returns_user_when_exists() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        entityManager.persistAndFlush(user);

        // When
        Optional<User> found = userRepository.findByUsername("testuser");

        // Then
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void findByUsername_returns_empty_when_not_exists() {
        // When
        Optional<User> found = userRepository.findByUsername("nonexistent");

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void existsByUsername_returns_true_when_exists() {
        // Given
        User user = new User();
        user.setUsername("existinguser");
        user.setName("Existing User");
        user.setEmail("existing@example.com");
        user.setPassword("password");
        entityManager.persistAndFlush(user);

        // When
        boolean exists = userRepository.existsByUsername("existinguser");

        // Then
        assertTrue(exists);
    }

    @Test
    void existsByUsername_returns_false_when_not_exists() {
        // When
        boolean exists = userRepository.existsByUsername("nonexistent");

        // Then
        assertFalse(exists);
    }

    @Test
    void existsByEmail_returns_true_when_exists() {
        // Given
        User user = new User();
        user.setUsername("user");
        user.setName("Test User");
        user.setEmail("existing@example.com");
        user.setPassword("password");
        entityManager.persistAndFlush(user);

        // When
        boolean exists = userRepository.existsByEmail("existing@example.com");

        // Then
        assertTrue(exists);
    }

    @Test
    void existsByEmail_returns_false_when_not_exists() {
        // When
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        // Then
        assertFalse(exists);
    }
} 