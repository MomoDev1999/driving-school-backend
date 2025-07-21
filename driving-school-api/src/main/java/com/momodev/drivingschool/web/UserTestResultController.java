package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.dto.UserTestResultRequest;
import com.momodev.drivingschool.dto.UserTestResultResponse;
import com.momodev.drivingschool.service.UserTestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-results")
@RequiredArgsConstructor
public class UserTestResultController {

    private final UserTestResultService service;

    @PostMapping
    public ResponseEntity<Void> saveTestResult(@RequestBody UserTestResultRequest request,
            @AuthenticationPrincipal User user) {
        service.saveTestResult(user, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-results")
    public ResponseEntity<List<UserTestResultResponse>> getMyResults(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getResultsByUser(user.getId()));
    }
}
