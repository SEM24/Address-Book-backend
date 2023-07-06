package com.chisoftware.contact.handler.validator;

import com.chisoftware.contact.handler.annotation.ValidPhoneList;
import com.chisoftware.user.handler.exception.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidPhoneListValidator implements ConstraintValidator<ValidPhoneList, List<String>> {
    @Override

    public boolean isValid(List<String> phones, ConstraintValidatorContext context) {
        if (phones == null || phones.isEmpty()) {
            return true; // Empty list is considered valid
        }

        for (String phone : phones) {
            if (!isValidPhone(phone)) {
                throw new BadRequestException("Invalid phone syntax.");
            }
        }

        return true; // All phone numbers are valid
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^\\+380\\d{9}$");
    }
}