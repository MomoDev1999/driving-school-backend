package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
