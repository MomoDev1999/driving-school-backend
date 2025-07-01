package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.UserConfiguration;
import com.momodev.drivingschool.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserConfigurationRepository extends JpaRepository<UserConfiguration, Integer> {
    Optional<UserConfiguration> findByUser(User user);
}
