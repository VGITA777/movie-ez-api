package com.prince.movieezapi.user.validators;

import com.prince.movieezapi.user.validators.annotations.Alphanumeric;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlphanumericValidator implements ConstraintValidator<Alphanumeric, String> {

    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(ALPHANUMERIC_REGEX);
    }
}
