package com.prince.movieezapi.security.ratelimit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prince.movieezapi.security.authenticationtokens.MovieEzFullyAuthenticatedUser;
import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.shared.utilities.SecurityUtils;
import io.github.resilience4j.ratelimiter.RateLimiter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import java.io.IOException;

/**
 * Implementation of the {@linkplain RateLimiterFilter} that applies rate limiting based on user roles and IP addresses.
 */
public class RateLimiterFilterImpl extends RateLimiterFilter {

    private static final SecurityContextHolderStrategy contextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private static final ObjectMapper mapper = new ObjectMapper();
    private final RateLimiterService rateLimiterService;

    public RateLimiterFilterImpl(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RateLimiterIdentifier rateLimiterIdentifier;
        var requestURI = request.getRequestURI();
        var identifier = getIp(request);

        logger.debug("Processing rate limiting for user: " + identifier + " and request URI: " + requestURI);

        if (!SecurityUtils.isAuthenticated()) {
            rateLimiterIdentifier = new RateLimiterIdentifier(identifier, RateLimiterUserRoles.GUEST, null);
        } else {
            var authentication = (MovieEzFullyAuthenticatedUser) contextHolderStrategy.getContext().getAuthentication();
            var role = RateLimiterUserRoles.from(authentication.getHighestPriorityRole());
            rateLimiterIdentifier = new RateLimiterIdentifier(identifier, role, authentication.getDetails());
        }

        var rateLimiter = getOrCreateRateLimiter(rateLimiterIdentifier);

        if (!rateLimiter.acquirePermission()) {
            returnFailResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    protected String getIp(HttpServletRequest request) {
        // In production, consider using X-Forwarded-For header
        // to get the real client IP behind proxies/load balancers
        return request.getRemoteAddr();
    }

    private RateLimiter getOrCreateRateLimiter(RateLimiterIdentifier identifier) {
        var rateLimiter = rateLimiterService.get(identifier);
        return (rateLimiter == null) ? rateLimiterService.create(identifier).getRateLimiter() : rateLimiter.getRateLimiter();
    }

    private void returnFailResponse(HttpServletResponse response) throws IOException {
        var message = ServerGenericResponse.failure("Too many requests. Please try again later.", null);
        response.setStatus(429);
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(message));
    }
}
