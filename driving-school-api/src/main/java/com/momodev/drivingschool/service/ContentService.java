package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.*;
import com.momodev.drivingschool.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ContentService {

    private final ContentRepository contentRepo;
    private final UserContentRepository userContentRepo;

    public ContentService(ContentRepository contentRepo, UserContentRepository userContentRepo) {
        this.contentRepo = contentRepo;
        this.userContentRepo = userContentRepo;
    }

    public Content getContentById(Integer id) {
        return contentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contenido no encontrado"));
    }

    public List<Content> getAllContent() {
        return contentRepo.findAll();
    }

    public void markContentAsRead(User user, Content content) {
        UserContent registro = userContentRepo.findAll().stream()
                .filter(uc -> uc.getUser().getId().equals(user.getId())
                        && uc.getContent().getId().equals(content.getId()))
                .findFirst()
                .orElse(UserContent.builder()
                        .user(user)
                        .content(content)
                        .build());

        registro.setReadAt(new Timestamp(System.currentTimeMillis()));
        userContentRepo.save(registro);
    }
}
