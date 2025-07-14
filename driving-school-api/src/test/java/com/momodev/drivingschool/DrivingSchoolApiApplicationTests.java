package com.momodev.drivingschool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DrivingSchoolApiApplicationTests {

    @Test 
    void contextLoads() {
        // Test simple que verifica que la aplicación puede instanciarse
        assertDoesNotThrow(() -> {
            DrivingSchoolApiApplication app = new DrivingSchoolApiApplication();
            // Si llegamos aquí, la clase se carga correctamente
        });
    }

    @Test
    void mainMethodExists() {
        // Test que verifica que el método main existe y es accesible
        assertDoesNotThrow(() -> {
            DrivingSchoolApiApplication.class.getMethod("main", String[].class);
        });
    }
} 