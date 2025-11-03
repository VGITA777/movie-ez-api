package com.prince.movieezapi.user.validators;

import com.prince.movieezapi.user.validators.annotations.Required;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class RequiredValidator implements ConstraintValidator<Required, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return !Objects.equals(value, null);
    }
}
