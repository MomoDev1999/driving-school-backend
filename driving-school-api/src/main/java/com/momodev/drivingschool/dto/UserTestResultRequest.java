package com.momodev.drivingschool.dto;

import lombok.Data;

@Data
public class UserTestResultRequest {
    private String mode;
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
}
