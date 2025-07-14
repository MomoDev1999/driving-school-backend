package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.Category;
import com.momodev.drivingschool.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryRepository categoryRepository;
    
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryController = new CategoryController(categoryRepository);
    }

    @Test
    void getAll_returns_category_list() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1);
        category1.setCategory("Teoría");
        
        Category category2 = new Category();
        category2.setId(2);
        category2.setCategory("Práctica");
        
        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        // Act
        List<Category> categories = categoryController.getAll();

        // Assert
        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertEquals("Teoría", categories.get(0).getCategory());
        assertEquals("Práctica", categories.get(1).getCategory());
    }
}
