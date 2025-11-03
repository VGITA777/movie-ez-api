package com.prince.movieezapi.user.validators.annotations;

import com.prince.movieezapi.user.validators.RequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = {RequiredValidator.class})
public @interface Required {
    String message() default "This field is required!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
