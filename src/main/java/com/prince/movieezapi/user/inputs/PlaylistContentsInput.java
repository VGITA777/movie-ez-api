package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.ListRegexMatch;
import com.prince.movieezapi.user.validators.annotations.Required;

import java.util.List;

public record PlaylistContentsInput(
        @ListRegexMatch(matchAll = true, patterns = {"^[a-zA-Z0-9]+$"}, message = "trackIds must be alphanumeric strings")
        @Required(message = "trackIds field is required")
        List<String> trackIds
) {
}
