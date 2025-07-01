package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Integer> {
}
