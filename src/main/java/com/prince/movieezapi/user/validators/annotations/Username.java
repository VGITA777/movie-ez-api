package com.prince.movieezapi.user.validators.annotations;

import com.prince.movieezapi.user.validators.RequiredValidator;
import com.prince.movieezapi.user.validators.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = {UsernameValidator.class})
public @interface Username {
    String message() default "Invalid or Malformed Username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minLength() default 8;

    int maxLength() default 32;
}
