package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.Question;
import com.momodev.drivingschool.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

    private final QuestionRepository repo;

    public QuestionService(QuestionRepository repo) {
        this.repo = repo;
    }

    public List<Question> getRandomQuestions(int count) {
        List<Question> all = repo.findAll();
        Collections.shuffle(all);
        return all.stream().limit(count).toList();
    }

    public List<Question> getRandomQuestionsByCategories(int count, List<Integer> categoryIds) {
        List<Question> filtered = repo.findByCategoryIdIn(categoryIds);
        Collections.shuffle(filtered);

        if (filtered.size() < count) {
            throw new IllegalArgumentException("No hay suficientes preguntas para esa combinación.");
        }

        return filtered.stream().limit(count).toList();
    }

}
