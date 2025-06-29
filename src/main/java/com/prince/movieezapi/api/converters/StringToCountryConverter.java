package com.prince.movieezapi.api.converters;

import com.prince.movieezapi.api.models.enums.Country;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCountryConverter implements Converter<String, Country> {
    @Override
    public Country convert(String source) {
        return Country.fromValue(source);
    }
}
