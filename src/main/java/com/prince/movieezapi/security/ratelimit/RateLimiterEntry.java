package com.prince.movieezapi.security.ratelimit;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter @Setter @AllArgsConstructor public class RateLimiterEntry {
    private Instant expiryTime;
    private RateLimiterIdentifier rateLimiterIdentifier;
    private RateLimiter rateLimiter;
}
