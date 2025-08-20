package com.prince.movieezapi.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prince.movieezapi.security.models.ServerMessageModel;
import com.prince.movieezapi.security.tokens.EzMovieAuthenticationToken;
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
public class CustomHeaderFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "X-Ez-Movie";
    public static final String HEADER_VALUE = "WowThisGottaBeTheBestSecurityMeasure101";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!Objects.equals(request.getHeader(HEADER_NAME), HEADER_VALUE)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.getWriter().write(new ObjectMapper().writeValueAsString(new ServerMessageModel("Unauthorized")));
            return;
        }

        Authentication authentication = new EzMovieAuthenticationToken();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }
}
