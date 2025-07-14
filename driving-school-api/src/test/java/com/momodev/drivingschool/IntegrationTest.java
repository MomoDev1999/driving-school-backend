package com.momodev.drivingschool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.momodev.drivingschool.dto.AuthRequest;
import com.momodev.drivingschool.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class IntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Test
    void testApplicationContextLoads() {
        // This test verifies that the Spring application context loads successfully
        assertNotNull(webApplicationContext);
    }

    @Test
    void testObjectMapperIsAvailable() {
        // This test verifies that ObjectMapper is available
        assertNotNull(objectMapper);
    }
} 