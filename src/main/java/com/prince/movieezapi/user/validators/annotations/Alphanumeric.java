package com.prince.movieezapi.user.validators.annotations;

import com.prince.movieezapi.user.validators.AlphanumericValidator;
import com.prince.movieezapi.user.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {AlphanumericValidator.class})
public @interface Alphanumeric {
    String message() default "{constraint.Alphanumeric.message}";

    @Size
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
