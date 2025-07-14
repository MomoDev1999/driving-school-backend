package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.Question;
import com.momodev.drivingschool.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    @Mock
    private QuestionService questionService;
    
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        questionController = new QuestionController(questionService);
    }

    @Test
    void getRandomQuestions_returns_question_list() {
        // Arrange
        Question question1 = new Question();
        question1.setId(1);
        question1.setStatement("¿Pregunta 1?");
        
        Question question2 = new Question();
        question2.setId(2);
        question2.setStatement("¿Pregunta 2?");
        
        when(questionService.getRandomQuestions(10)).thenReturn(List.of(question1, question2));

        // Act
        ResponseEntity<List<Question>> response = questionController.getRandomQuestions(10);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Question> questions = response.getBody();
        assertNotNull(questions);
        assertEquals(2, questions.size());
        assertEquals("¿Pregunta 1?", questions.get(0).getStatement());
        assertEquals("¿Pregunta 2?", questions.get(1).getStatement());
    }

    @Test
    void getByCategory_returns_questions_by_categories() {
        // Arrange
        Question question = new Question();
        question.setId(1);
        question.setStatement("¿Pregunta de categoría?");
        
        List<Integer> categoryIds = List.of(1, 2);
        when(questionService.getRandomQuestionsByCategories(5, categoryIds)).thenReturn(List.of(question));

        // Act
        ResponseEntity<List<Question>> response = questionController.getByCategory(5, categoryIds);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Question> questions = response.getBody();
        assertNotNull(questions);
        assertEquals(1, questions.size());
        assertEquals("¿Pregunta de categoría?", questions.get(0).getStatement());
    }
}
