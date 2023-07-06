package com.chisoftware.contact.handler.validator;

import com.chisoftware.contact.handler.ContactValidationProperties;
import com.chisoftware.contact.handler.annotation.ValidEmailList;
import com.chisoftware.user.handler.exception.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ValidEmailListValidator implements ConstraintValidator<ValidEmailList, List<String>> {
    private ContactValidationProperties validationProperties;

    @Override
    public boolean isValid(List<String> emails, ConstraintValidatorContext context) {
        if (emails == null || emails.isEmpty()) {
            return true; // Empty list is considered valid
        }

        for (String email : emails) {
            if (!isValidEmail(email)) {
                throw new BadRequestException("Invalid email syntax.");
            }
        }

        return true; // All emails are valid
    }

    private boolean isValidEmail(String email) {
        return email.matches(validationProperties.emailRegex());
    }
}