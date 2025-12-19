package com.prince.movieezapi.shared.models.responses;

import com.prince.movieezapi.shared.models.ErrorModel;

import java.util.List;

public record ServerErrorResponse(String message, List<ErrorModel> errors) {
    public ServerErrorResponse(String message, ErrorModel errorModel) {
        this(message, List.of(errorModel));
    }
}
