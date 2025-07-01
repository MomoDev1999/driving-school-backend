package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContentRepository extends JpaRepository<UserContent, Integer> {
}
