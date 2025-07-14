package com.momodev.drivingschool.service;

import com.momodev.drivingschool.domain.Content;
import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserContent;
import com.momodev.drivingschool.repository.ContentRepository;
import com.momodev.drivingschool.repository.UserContentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContentServiceTest {

    private ContentRepository contentRepo;
    private UserContentRepository userContentRepo;
    private ContentService service;

    @BeforeEach
    void setUp() {
        contentRepo = mock(ContentRepository.class);
        userContentRepo = mock(UserContentRepository.class);
        service = new ContentService(contentRepo, userContentRepo);
    }

    @Test
    void getContentById_returnsContent() {
        Content c = new Content();
        when(contentRepo.findById(1)).thenReturn(Optional.of(c));

        Content result = service.getContentById(1);
        assertThat(result).isSameAs(c);
    }

    @Test
    void getContentById_notFound_throws() {
        when(contentRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getContentById(99))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getAllContent_returnsList() {
        List<Content> list = Arrays.asList(new Content(), new Content());
        when(contentRepo.findAll()).thenReturn(list);

        List<Content> result = service.getAllContent();
        assertThat(result).hasSize(2);
    }

    @Test
    void markContentAsRead_newRecord_savedWithTimestamp() {
        User user = User.builder().id(1).build();
        Content content = Content.builder().id(2).build();
        when(userContentRepo.findAll()).thenReturn(List.of());
        when(userContentRepo.save(any())).thenReturn(null);

        service.markContentAsRead(user, content);

        verify(userContentRepo).save(argThat(
                uc -> uc.getUser().getId().equals(1)
                        && uc.getContent().getId().equals(2)
                        && uc.getReadAt() != null
        ));
    }

    @Test
    void markContentAsRead_existingRecord_updatesTimestamp() {
        User user = User.builder().id(1).build();
        Content content = Content.builder().id(2).build();
        UserContent uc = UserContent.builder()
                .user(user).content(content).readAt(null).build();

        when(userContentRepo.findAll()).thenReturn(List.of(uc));

        service.markContentAsRead(user, content);

        assertThat(uc.getReadAt()).isInstanceOf(Timestamp.class);
        verify(userContentRepo).save(uc);
    }
}
