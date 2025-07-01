package com.momodev.drivingschool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeRequest(
        @NotBlank(message = "La contraseña actual no puede estar vacía") String oldPassword,

        @NotBlank(message = "La nueva contraseña no puede estar vacía") @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres") String newPassword) {
}
