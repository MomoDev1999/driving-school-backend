package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.Category;
import com.momodev.drivingschool.repository.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// CategoryController.java
@RestController

@RequestMapping("/api/categories")

public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
