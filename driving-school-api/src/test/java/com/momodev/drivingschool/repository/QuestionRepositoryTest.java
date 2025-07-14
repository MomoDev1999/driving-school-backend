package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.Category;
import com.momodev.drivingschool.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class QuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void save_question_returns_saved_question() {
        // Given
        Category category = new Category();
        category.setCategory("Test Category");
        category.setDescription("Test Description");
        Category savedCategory = entityManager.persistAndFlush(category);

        Question question = new Question();
        question.setStatement("¿Cuál es la velocidad máxima en zona urbana?");
        question.setCorrect("50 km/h");
        question.setWrongOne("60 km/h");
        question.setWrongTwo("70 km/h");
        question.setWrongThree("80 km/h");
        question.setCategory(savedCategory);

        // When
        Question saved = questionRepository.save(question);

        // Then
        assertNotNull(saved.getId());
        assertEquals("¿Cuál es la velocidad máxima en zona urbana?", saved.getStatement());
        assertEquals("50 km/h", saved.getCorrect());
        assertEquals(savedCategory.getId(), saved.getCategory().getId());
    }

    @Test
    void findByCategoryIdIn_returns_questions_for_categories() {
        // Given
        Category category1 = new Category();
        category1.setCategory("Categoría 1");
        Category savedCategory1 = entityManager.persistAndFlush(category1);

        Category category2 = new Category();
        category2.setCategory("Categoría 2");
        Category savedCategory2 = entityManager.persistAndFlush(category2);

        Question question1 = new Question();
        question1.setStatement("Pregunta 1");
        question1.setCorrect("Correcta 1");
        question1.setWrongOne("Incorrecta 1");
        question1.setWrongTwo("Incorrecta 2");
        question1.setWrongThree("Incorrecta 3");
        question1.setCategory(savedCategory1);
        entityManager.persistAndFlush(question1);

        Question question2 = new Question();
        question2.setStatement("Pregunta 2");
        question2.setCorrect("Correcta 2");
        question2.setWrongOne("Incorrecta 4");
        question2.setWrongTwo("Incorrecta 5");
        question2.setWrongThree("Incorrecta 6");
        question2.setCategory(savedCategory2);
        entityManager.persistAndFlush(question2);

        // When
        List<Question> questions = questionRepository.findByCategoryIdIn(
            Arrays.asList(savedCategory1.getId(), savedCategory2.getId())
        );

        // Then
        assertEquals(2, questions.size());
        assertTrue(questions.stream().anyMatch(q -> q.getStatement().equals("Pregunta 1")));
        assertTrue(questions.stream().anyMatch(q -> q.getStatement().equals("Pregunta 2")));
    }

    @Test
    void findByCategoryIdIn_returns_empty_when_no_categories_match() {
        // When
        List<Question> questions = questionRepository.findByCategoryIdIn(Arrays.asList(999, 1000));

        // Then
        assertTrue(questions.isEmpty());
    }
} 