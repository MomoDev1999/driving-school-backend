package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.Question;
import com.momodev.drivingschool.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    private QuestionRepository repo;
    private QuestionService service;

    @BeforeEach
    void setUp() {
        repo = mock(QuestionRepository.class);
        service = new QuestionService(repo);
    }

    @Test
    void getRandomQuestions_shuffles_andLimits() {
        List<Question> questions = Arrays.asList(new Question(), new Question(), new Question());
        when(repo.findAll()).thenReturn(questions);

        List<Question> result = service.getRandomQuestions(2);

        assertThat(result).hasSize(2);
    }

    @Test
    void getRandomQuestionsByCategories_returnsLimitedList() {
        List<Question> filtered = Arrays.asList(new Question(), new Question());
        when(repo.findByCategoryIdIn(List.of(1, 2))).thenReturn(filtered);

        List<Question> result = service.getRandomQuestionsByCategories(2, List.of(1, 2));
        assertThat(result).hasSize(2);
    }

    @Test
    void getRandomQuestionsByCategories_notEnoughQuestions_throws() {
        List<Question> filtered = List.of(new Question());
        when(repo.findByCategoryIdIn(List.of(1, 2))).thenReturn(filtered);

        assertThatThrownBy(() -> service.getRandomQuestionsByCategories(3, List.of(1, 2)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
