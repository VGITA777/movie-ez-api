package com.prince.movieezapi.api.models.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum MediaType {
    MOVIE("movie"), TV("tv"), PERSON("person");

    public static final Map<String, MediaType> MEDIA_TYPE_MAP = Arrays.stream(MediaType.values()).collect(Collectors.toMap(MediaType::getType, type -> type));
    private final String type;

    MediaType(String type) {
        this.type = type;
    }

    public static MediaType fromValue(String type) {
        return MEDIA_TYPE_MAP.getOrDefault(type, null);
    }

}
