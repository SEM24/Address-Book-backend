package com.chisoftware.contact.model.dto;

import com.chisoftware.contact.handler.annotation.ValidEmail;
import com.chisoftware.contact.handler.annotation.ValidEmailList;
import com.chisoftware.contact.handler.annotation.ValidPhone;
import com.chisoftware.contact.handler.annotation.ValidPhoneList;
import com.chisoftware.user.handler.annotation.LatinText;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ContactDTO(
        @LatinText
        String name,
        @NotEmpty(message = "Emails must not be empty")
        @ValidEmailList
        List<@ValidEmail String> emails,
        @ValidPhoneList
        List<@ValidPhone String> phones
) {
}
