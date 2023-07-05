package com.chisoftware.contact.model.dto;

import com.chisoftware.user.handler.annotation.LatinText;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CreateContactDTO(
        @NotBlank
        @LatinText
        String name,
        Set<String> emails,
        Set<String> phones
) {
}
