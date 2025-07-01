package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserConfiguration;
import com.momodev.drivingschool.repository.UserConfigurationRepository;
import com.momodev.drivingschool.repository.UserRepository;
import com.momodev.drivingschool.util.ApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final UserConfigurationRepository userConfigRepo;

    public UserService(UserRepository repo,
            PasswordEncoder encoder,
            UserConfigurationRepository userConfigRepo) {
        this.repo = repo;
        this.encoder = encoder;
        this.userConfigRepo = userConfigRepo;
    }

    public User save(User u) {
        // Encriptamos la contraseña
        u.setPassword(encoder.encode(u.getPassword()));
        try {
            // Guardamos el usuario
            User guardado = repo.save(u);

            // Creamos configuración por defecto (ej. darkMode = false)
            UserConfiguration config = UserConfiguration.builder()
                    .user(guardado)
                    .darkMode(false)
                    .build();
            userConfigRepo.save(config);

            return guardado;
        } catch (DataIntegrityViolationException ex) {
            // Violación de restricciones únicas: username o email ya existen
            throw new ApiException("El nombre de usuario o correo ya está registrado");
        }
    }

    public void changePassword(User user, String oldPwd, String newPwd) {
        if (!encoder.matches(oldPwd, user.getPassword())) {
            throw new ApiException("La contraseña actual no coincide");
        }
        user.setPassword(encoder.encode(newPwd));
        repo.save(user);
    }
}
