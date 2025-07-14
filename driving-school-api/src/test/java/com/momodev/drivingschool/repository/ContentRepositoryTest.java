package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.Content;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ContentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContentRepository contentRepository;

    @Test
    void save_content_returns_saved_content() {
        // Given
        Content content = new Content();
        content.setTitle("Normas de Tránsito");
        content.setParagraph("Las normas de tránsito son fundamentales para la seguridad vial...");

        // When
        Content saved = contentRepository.save(content);

        // Then
        assertNotNull(saved.getId());
        assertEquals("Normas de Tránsito", saved.getTitle());
        assertEquals("Las normas de tránsito son fundamentales para la seguridad vial...", saved.getParagraph());
    }

    @Test
    void findById_returns_content_when_exists() {
        // Given
        Content content = new Content();
        content.setTitle("Señales de Tránsito");
        content.setParagraph("Las señales de tránsito indican las reglas a seguir...");
        Content saved = entityManager.persistAndFlush(content);

        // When
        Content found = contentRepository.findById(saved.getId()).orElse(null);

        // Then
        assertNotNull(found);
        assertEquals("Señales de Tránsito", found.getTitle());
        assertEquals("Las señales de tránsito indican las reglas a seguir...", found.getParagraph());
    }

    @Test
    void findAll_returns_all_contents() {
        // Given
        Content content1 = new Content();
        content1.setTitle("Contenido 1");
        content1.setParagraph("Descripción 1");
        
        Content content2 = new Content();
        content2.setTitle("Contenido 2");
        content2.setParagraph("Descripción 2");
        
        entityManager.persistAndFlush(content1);
        entityManager.persistAndFlush(content2);

        // When
        List<Content> contents = contentRepository.findAll();

        // Then
        assertTrue(contents.size() >= 2);
        assertTrue(contents.stream().anyMatch(c -> c.getTitle().equals("Contenido 1")));
        assertTrue(contents.stream().anyMatch(c -> c.getTitle().equals("Contenido 2")));
    }

    @Test
    void delete_content_removes_from_database() {
        // Given
        Content content = new Content();
        content.setTitle("Contenido a eliminar");
        content.setParagraph("Descripción");
        Content saved = entityManager.persistAndFlush(content);

        // When
        contentRepository.deleteById(saved.getId());
        entityManager.flush();

        // Then
        assertFalse(contentRepository.findById(saved.getId()).isPresent());
    }
} 