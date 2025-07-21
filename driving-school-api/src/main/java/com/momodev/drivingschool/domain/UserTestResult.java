package com.momodev.drivingschool.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_test_results")
public class UserTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mode; // 10, 30, personalizado

    @Column(name = "total_questions")
    private int totalQuestions;

    @Column(name = "correct_answers")
    private int correctAnswers;

    @Column(name = "wrong_answers")
    private int wrongAnswers;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
