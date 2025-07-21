package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.Content;
import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserContent;
import com.momodev.drivingschool.repository.ContentRepository;
import com.momodev.drivingschool.repository.UserContentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        Optional<UserContent> optional = userContentRepo.findByUser_IdAndContent_Id(user.getId(), content.getId());

        UserContent registro = optional.orElse(
                UserContent.builder()
                        .user(user)
                        .content(content)
                        .build());

        registro.setReadAt(new Timestamp(System.currentTimeMillis()));
        userContentRepo.save(registro);
    }

    public Set<Integer> getReadContentIdsByUserId(Integer userId) {
        return userContentRepo.findByUser_Id(userId).stream()
                .map(uc -> uc.getContent().getId())
                .collect(Collectors.toSet());
    }
}
