package com.prince.movieezapi.security.models;

import lombok.Builder;

@Builder
public record OttMailModel(String recipient, String tokenValue) {

}
