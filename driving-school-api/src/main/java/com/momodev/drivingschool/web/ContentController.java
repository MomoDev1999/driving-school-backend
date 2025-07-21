package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.Content;
import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.dto.ContentWithReadStatus;
import com.momodev.drivingschool.service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> markAsRead(
            @AuthenticationPrincipal User user,
            @PathVariable Integer id) {

        Content content = contentService.getContentById(id);
        contentService.markContentAsRead(user, content);
        return ResponseEntity.ok().body("{\"message\": \"Contenido marcado como leído\"}");
    }

    @GetMapping("/with-status")
    public ResponseEntity<List<ContentWithReadStatus>> getContentWithStatus(
            @AuthenticationPrincipal User user) {

        List<Content> allContent = contentService.getAllContent();
        Set<Integer> readContentIds = contentService.getReadContentIdsByUserId(user.getId());

        List<ContentWithReadStatus> response = allContent.stream()
                .map(c -> new ContentWithReadStatus(
                        c.getId(),
                        c.getTitle(),
                        c.getParagraph(),
                        readContentIds.contains(c.getId())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
