package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.Content;
import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.domain.UserContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserContentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserContentRepository userContentRepository;

    @Test
    void save_userContent_returns_saved_userContent() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = entityManager.persistAndFlush(user);

        Content content = new Content();
        content.setTitle("Test Content");
        content.setParagraph("Test paragraph");
        Content savedContent = entityManager.persistAndFlush(content);

        UserContent userContent = new UserContent();
        userContent.setUser(savedUser);
        userContent.setContent(savedContent);
        userContent.setReadAt(new Timestamp(System.currentTimeMillis()));

        // When
        UserContent saved = userContentRepository.save(userContent);

        // Then
        assertNotNull(saved.getId());
        assertEquals(savedUser.getId(), saved.getUser().getId());
        assertEquals(savedContent.getId(), saved.getContent().getId());
        assertNotNull(saved.getReadAt());
    }

    @Test
    void findById_returns_userContent_when_exists() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = entityManager.persistAndFlush(user);

        Content content = new Content();
        content.setTitle("Test Content");
        content.setParagraph("Test paragraph");
        Content savedContent = entityManager.persistAndFlush(content);

        UserContent userContent = new UserContent();
        userContent.setUser(savedUser);
        userContent.setContent(savedContent);
        userContent.setReadAt(new Timestamp(System.currentTimeMillis()));
        UserContent saved = entityManager.persistAndFlush(userContent);

        // When
        UserContent found = userContentRepository.findById(saved.getId()).orElse(null);

        // Then
        assertNotNull(found);
        assertEquals(savedUser.getId(), found.getUser().getId());
        assertEquals(savedContent.getId(), found.getContent().getId());
        assertNotNull(found.getReadAt());
    }

    @Test
    void findAll_returns_all_userContents() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = entityManager.persistAndFlush(user);

        Content content1 = new Content();
        content1.setTitle("Content 1");
        content1.setParagraph("Paragraph 1");
        Content savedContent1 = entityManager.persistAndFlush(content1);

        Content content2 = new Content();
        content2.setTitle("Content 2");
        content2.setParagraph("Paragraph 2");
        Content savedContent2 = entityManager.persistAndFlush(content2);

        UserContent userContent1 = new UserContent();
        userContent1.setUser(savedUser);
        userContent1.setContent(savedContent1);
        userContent1.setReadAt(new Timestamp(System.currentTimeMillis()));
        entityManager.persistAndFlush(userContent1);

        UserContent userContent2 = new UserContent();
        userContent2.setUser(savedUser);
        userContent2.setContent(savedContent2);
        userContent2.setReadAt(new Timestamp(System.currentTimeMillis()));
        entityManager.persistAndFlush(userContent2);

        // When
        List<UserContent> userContents = userContentRepository.findAll();

        // Then
        assertTrue(userContents.size() >= 2);
        assertTrue(userContents.stream().anyMatch(uc -> uc.getContent().getTitle().equals("Content 1")));
        assertTrue(userContents.stream().anyMatch(uc -> uc.getContent().getTitle().equals("Content 2")));
    }

    @Test
    void delete_userContent_removes_from_database() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = entityManager.persistAndFlush(user);

        Content content = new Content();
        content.setTitle("Test Content");
        content.setParagraph("Test paragraph");
        Content savedContent = entityManager.persistAndFlush(content);

        UserContent userContent = new UserContent();
        userContent.setUser(savedUser);
        userContent.setContent(savedContent);
        userContent.setReadAt(new Timestamp(System.currentTimeMillis()));
        UserContent saved = entityManager.persistAndFlush(userContent);

        // When
        userContentRepository.deleteById(saved.getId());
        entityManager.flush();

        // Then
        assertFalse(userContentRepository.findById(saved.getId()).isPresent());
    }
} 