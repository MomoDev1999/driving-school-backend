package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserConfiguration;
import com.momodev.drivingschool.repository.UserConfigurationRepository;
import com.momodev.drivingschool.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock UserRepository repo;
    @Mock PasswordEncoder encoder;
    @Mock UserConfigurationRepository userConfigRepo;
    @InjectMocks UserService service;

    @BeforeEach void init(){ MockitoAnnotations.openMocks(this); }

    @Test void save_encodes_password_and_saves() {
        User u = new User(); u.setPassword("raw");
        when(encoder.encode("raw")).thenReturn("hash");
        when(repo.save(u)).thenReturn(u);
        when(userConfigRepo.save(any(UserConfiguration.class))).thenReturn(new UserConfiguration());

        User saved = service.save(u);

        assertThat(saved.getPassword()).isEqualTo("hash");
        verify(repo).save(u);
        verify(userConfigRepo).save(any(UserConfiguration.class));
    }

    @Test void changePassword_updates_hashed_value() {
        User u = new User(); u.setId(1); u.setPassword("old");
        when(encoder.matches("old", "old")).thenReturn(true);
        when(encoder.encode("new")).thenReturn("hash");

        service.changePassword(u, "old", "new");

        assertThat(u.getPassword()).isEqualTo("hash");
        verify(repo).save(u);
    }
}
