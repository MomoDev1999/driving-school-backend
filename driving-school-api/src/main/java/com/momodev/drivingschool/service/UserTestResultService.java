package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserTestResult;
import com.momodev.drivingschool.dto.UserTestResultRequest;
import com.momodev.drivingschool.dto.UserTestResultResponse;
import com.momodev.drivingschool.repository.UserTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTestResultService {

    private final UserTestResultRepository userTestResultRepository;

    public UserTestResult saveTestResult(User user, UserTestResultRequest request) {
        UserTestResult result = new UserTestResult();
        result.setUser(user);
        result.setMode(request.getMode());
        result.setTotalQuestions(request.getTotalQuestions());
        result.setCorrectAnswers(request.getCorrectAnswers());
        result.setWrongAnswers(request.getWrongAnswers());
        result.setCreatedAt(LocalDateTime.now());

        return userTestResultRepository.save(result);
    }

    public List<UserTestResultResponse> getResultsByUser(Integer userId) {
        return userTestResultRepository.findByUser_IdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private UserTestResultResponse mapToDto(UserTestResult result) {
        UserTestResultResponse dto = new UserTestResultResponse();
        dto.setId(result.getId());
        dto.setMode(result.getMode());
        dto.setTotalQuestions(result.getTotalQuestions());
        dto.setCorrectAnswers(result.getCorrectAnswers());
        dto.setWrongAnswers(result.getWrongAnswers());
        dto.setCreatedAt(result.getCreatedAt());
        return dto;
    }
}
