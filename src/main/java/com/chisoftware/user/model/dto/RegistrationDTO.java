package com.chisoftware.user.model.dto;

import com.chisoftware.user.handler.annotation.LatinText;
import jakarta.validation.constraints.Size;

public record RegistrationDTO(
        @LatinText
        @Size(min = 1, max = 64, message = "Username must be less than 64 characters and more than 1")
        String username,
        @Size(min = 5, max = 64, message = "Password must be less than 64 characters and more than 5")
        String password) {
}
