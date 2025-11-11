package com.prince.movieezapi.security.filters;

import com.prince.movieezapi.security.authenticationtokens.MovieEzGuestAuthenticationToken;
import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.shared.utilities.BasicUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * A custom authentication filter for the /media/** endpoint.
 * This filter uses a custom header to identify if a request is authenticated.
 */
public class CustomSecurityHeaderFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "X-Ez-Movie";
    private static final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private byte[] headerBytesValue;

    public CustomSecurityHeaderFilter(String headerValue) {
        assert headerBytesValue != null : "Header bytes value cannot be null";
        this.headerBytesValue = headerValue.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader(HEADER_NAME);
        if (requestHeader == null) {
            sendJsonError(response, HttpStatus.UNAUTHORIZED.value(), "What are you doing?");
            return;
        }
        boolean ok = MessageDigest.isEqual(requestHeader.getBytes(StandardCharsets.UTF_8), headerBytesValue);
        if (!ok) {
            sendJsonError(response, HttpStatus.UNAUTHORIZED.value(), "Maybe try something else?");
            return;
        }

        Authentication authentication = new MovieEzGuestAuthenticationToken();
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    private void sendJsonError(HttpServletResponse response, int status, String message) {
        BasicUtils.sendJson(HttpStatus.valueOf(status), ServerGenericResponse.failure(message, null), response);
    }
}
