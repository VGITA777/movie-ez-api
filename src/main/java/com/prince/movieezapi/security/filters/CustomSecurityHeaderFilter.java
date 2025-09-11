package com.prince.movieezapi.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prince.movieezapi.security.tokens.MovieEzAuthenticationToken;
import com.prince.movieezapi.shared.responses.ServerGenericResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * Main authentication filter for the application.
 * Requires a custom header in the request to be authenticated.
 */
public class CustomSecurityHeaderFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "X-Ez-Movie";
    private String headerValue;

    public CustomSecurityHeaderFilter(String headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader(HEADER_NAME);
        if (!Objects.equals(requestHeader, headerValue)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(ServerGenericResponse.failure("Unauthorized", null)));
            return;
        }

        Authentication authentication = new MovieEzAuthenticationToken();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }
}
