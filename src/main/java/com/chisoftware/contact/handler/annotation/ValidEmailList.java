package com.chisoftware.contact.handler.annotation;

import com.chisoftware.contact.handler.validator.ValidEmailListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = {ValidEmailListValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailList {
    String message() default "Invalid email list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}