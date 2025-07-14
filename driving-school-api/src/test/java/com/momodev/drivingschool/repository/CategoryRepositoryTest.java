package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void save_category_returns_saved_category() {
        // Given
        Category category = new Category();
        category.setCategory("Señales de Tránsito");
        category.setDescription("Categoría para señales de tránsito");

        // When
        Category saved = categoryRepository.save(category);

        // Then
        assertNotNull(saved.getId());
        assertEquals("Señales de Tránsito", saved.getCategory());
        assertEquals("Categoría para señales de tránsito", saved.getDescription());
    }

    @Test
    void findById_returns_category_when_exists() {
        // Given
        Category category = new Category();
        category.setCategory("Normas de Conducción");
        category.setDescription("Normas básicas de conducción");
        Category saved = entityManager.persistAndFlush(category);

        // When
        Category found = categoryRepository.findById(saved.getId()).orElse(null);

        // Then
        assertNotNull(found);
        assertEquals("Normas de Conducción", found.getCategory());
        assertEquals("Normas básicas de conducción", found.getDescription());
    }

    @Test
    void findAll_returns_all_categories() {
        // Given
        Category category1 = new Category();
        category1.setCategory("Categoría 1");
        category1.setDescription("Descripción 1");
        
        Category category2 = new Category();
        category2.setCategory("Categoría 2");
        category2.setDescription("Descripción 2");
        
        entityManager.persistAndFlush(category1);
        entityManager.persistAndFlush(category2);

        // When
        List<Category> categories = categoryRepository.findAll();

        // Then
        assertTrue(categories.size() >= 2);
        assertTrue(categories.stream().anyMatch(c -> c.getCategory().equals("Categoría 1")));
        assertTrue(categories.stream().anyMatch(c -> c.getCategory().equals("Categoría 2")));
    }

    @Test
    void delete_category_removes_from_database() {
        // Given
        Category category = new Category();
        category.setCategory("Categoría a eliminar");
        category.setDescription("Descripción");
        Category saved = entityManager.persistAndFlush(category);

        // When
        categoryRepository.deleteById(saved.getId());
        entityManager.flush();

        // Then
        assertFalse(categoryRepository.findById(saved.getId()).isPresent());
    }
} 