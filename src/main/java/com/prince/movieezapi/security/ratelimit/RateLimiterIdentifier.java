package com.prince.movieezapi.security.ratelimit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class RateLimiterIdentifier implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private RateLimiterUserRoles role;
    private Object details;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RateLimiterIdentifier that = (RateLimiterIdentifier) o;
        return Objects.equals(id, that.id) && role == that.role && Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, details);
    }
}

