package com.momodev.drivingschool.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record RegisterRequest(
                @NotBlank(message = "El nombre de usuario no puede estar vacío") @Size(min = 4, max = 20, message = "El nombre de usuario debe tener entre 4 y 20 caracteres") String username,

                @NotBlank(message = "El nombre no puede estar vacío") String name,

                @NotBlank(message = "El correo electrónico no puede estar vacío") @Email(message = "El correo electrónico debe tener un formato válido") String email,

                @NotBlank(message = "La contraseña no puede estar vacía") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password,

                @NotNull(message = "La fecha de nacimiento es obligatoria") LocalDate birthday) {
}
