package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.*;
import com.momodev.drivingschool.service.ContentService;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<List<Content>> getAll() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Map<String, String>> markAsRead(
            @AuthenticationPrincipal User user,
            @PathVariable Integer id) {

        Content content = contentService.getContentById(id);
        contentService.markContentAsRead(user, content);
        return ResponseEntity.ok(Map.of("message", "Contenido marcado como leído"));
    }
}
