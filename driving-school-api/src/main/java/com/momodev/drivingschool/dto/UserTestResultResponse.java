package com.momodev.drivingschool.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTestResultResponse {
    private Long id;
    private String mode;
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private LocalDateTime createdAt;
}
