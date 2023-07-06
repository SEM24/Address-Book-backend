package com.chisoftware.contact.handler.annotation;

import com.chisoftware.contact.handler.validator.ValidPhoneListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = {ValidPhoneListValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneList {
    String message() default "Invalid phone list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}