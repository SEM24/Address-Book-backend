package com.chisoftware.contact.model.dto;

import com.chisoftware.user.handler.annotation.LatinText;

import java.util.List;

public record ContactDTO(
        @LatinText
        String name,
        List<String> emails,
        List<String> phones
) {
}
