package com.momodev.drivingschool.web;

import com.momodev.drivingschool.domain.Content;
import com.momodev.drivingschool.domain.User;
import com.momodev.drivingschool.service.ContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentControllerTest {

    @Mock
    private ContentService contentService;
    
    private ContentController contentController;

    @BeforeEach
    void setUp() {
        contentController = new ContentController(contentService);
    }

    @Test
    void getAll_returns_content_list() {
        // Arrange
        Content content1 = new Content();
        content1.setId(1);
        content1.setTitle("Contenido 1");
        
        Content content2 = new Content();
        content2.setId(2);
        content2.setTitle("Contenido 2");
        
        when(contentService.getAllContent()).thenReturn(List.of(content1, content2));

        // Act
        ResponseEntity<List<Content>> response = contentController.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Content> contents = response.getBody();
        assertNotNull(contents);
        assertEquals(2, contents.size());
        assertEquals("Contenido 1", contents.get(0).getTitle());
        assertEquals("Contenido 2", contents.get(1).getTitle());
    }

    @Test
    void getById_returns_content() {
        // Arrange
        Content content = new Content();
        content.setId(1);
        content.setTitle("Contenido Test");
        
        when(contentService.getContentById(1)).thenReturn(content);

        // Act
        ResponseEntity<Content> response = contentController.getById(1);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Content result = response.getBody();
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Contenido Test", result.getTitle());
    }

    @Test
    void markAsRead_returns_success_message() {
        // Arrange
        User user = new User();
        user.setId(1);
        
        Content content = new Content();
        content.setId(1);
        
        when(contentService.getContentById(1)).thenReturn(content);
        doNothing().when(contentService).markContentAsRead(user, content);

        // Act
        ResponseEntity<Map<String, String>> response = contentController.markAsRead(user, 1);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Map<String, String> result = response.getBody();
        assertNotNull(result);
        assertEquals("Contenido marcado como leído", result.get("message"));
        verify(contentService).markContentAsRead(user, content);
    }
}
