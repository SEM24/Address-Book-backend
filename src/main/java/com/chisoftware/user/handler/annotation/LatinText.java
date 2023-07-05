package com.chisoftware.user.handler.annotation;

import com.chisoftware.user.handler.validation.LatinTextValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {LatinTextValidator.class})
public @interface LatinText {

    String message() default "{Text.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}