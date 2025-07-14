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
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserConfigurationServiceTest {

    @Mock UserConfigurationRepository repo;
    @Mock UserRepository userRepo;
    @Mock Authentication authentication;
    @InjectMocks UserConfigurationService service;

    @BeforeEach void init(){ MockitoAnnotations.openMocks(this); }

    @Test void getConfiguration_creates_default_if_absent() {
        User u = new User(); u.setId(1);
        when(userRepo.findById(1)).thenReturn(Optional.of(u));
        when(repo.findByUser(u)).thenReturn(Optional.empty());
        when(repo.save(any(UserConfiguration.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserConfiguration cfg = service.getConfiguration(1);

        assertThat(cfg.getUser()).isSameAs(u);
        assertThat(cfg.getDarkMode()).isFalse();
        verify(repo).save(any());
    }

    @Test void updateDarkMode_updates_and_saves() {
        User u = new User(); u.setId(1);
        UserConfiguration cfg = new UserConfiguration(); 
        cfg.setDarkMode(false); 
        cfg.setUser(u);
        when(userRepo.findById(1)).thenReturn(Optional.of(u));
        when(repo.findByUser(u)).thenReturn(Optional.of(cfg));
        when(repo.save(any(UserConfiguration.class))).thenReturn(cfg);

        UserConfiguration updated = service.updateDarkMode(1, true);

        assertThat(updated.getDarkMode()).isTrue();
        verify(repo).save(cfg);
    }

    @Test void getByAuthenticatedUser_returns_configuration() {
        User u = new User(); u.setId(1);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(u));
        when(repo.findByUser(u)).thenReturn(Optional.empty());
        when(repo.save(any(UserConfiguration.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserConfiguration cfg = service.getByAuthenticatedUser(authentication);

        assertThat(cfg.getUser()).isSameAs(u);
        assertThat(cfg.getDarkMode()).isFalse();
    }
}
