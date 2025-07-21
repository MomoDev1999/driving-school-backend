package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.UserTestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTestResultRepository extends JpaRepository<UserTestResult, Long> {
    List<UserTestResult> findByUser_IdOrderByCreatedAtDesc(Integer userId);
}
