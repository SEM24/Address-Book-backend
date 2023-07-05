package com.chisoftware.user.handler.validation;

import com.chisoftware.user.handler.ValidationProperties;
import com.chisoftware.user.handler.annotation.LatinUsername;
import com.chisoftware.user.handler.exception.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class LatinUsernameValidator implements ConstraintValidator<LatinUsername, String> {
    private ValidationProperties validationProp;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        Pattern pattern =
                Pattern.compile(validationProp.usernameRegex());
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new BadRequestException("Username must be written in Latin!");
        }
        return true;
    }
}