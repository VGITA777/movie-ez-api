package com.prince.movieezapi.media.api.converters;

import com.prince.movieezapi.media.api.models.enums.Country;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component public class StringToCountryConverter implements Converter<String, Country> {
    @Override
    public Country convert(String source) {
        return Country.fromValue(source);
    }
}
