package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.Question;
import com.momodev.drivingschool.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping("/random")
    public ResponseEntity<List<Question>> getRandomQuestions(@RequestParam(defaultValue = "10") int count) {
        return ResponseEntity.ok(service.getRandomQuestions(count));
    }

    @GetMapping("/random/by-category")
    public ResponseEntity<List<Question>> getByCategory(
            @RequestParam int count,
            @RequestParam List<Integer> categoryIds) {
        return ResponseEntity.ok(service.getRandomQuestionsByCategories(count, categoryIds));
    }
}
