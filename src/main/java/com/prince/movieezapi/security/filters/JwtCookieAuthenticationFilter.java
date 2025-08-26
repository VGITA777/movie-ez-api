package com.prince.movieezapi.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class JwtCookieAuthenticationFilter extends OncePerRequestFilter {

    private static final SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
    private final NimbusJwtDecoder jwtDecoder;
    @Value("${security.jwt.key:movieez_token}")
    private String JWT_COOKIE_KEY;


    public JwtCookieAuthenticationFilter(NimbusJwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isUserAlreadyAuthenticated(strategy.getContext().getAuthentication())) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<Cookie> jwtCookie = getJwtCookie(request);

        if (jwtCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtCookie.get().getValue();
        try {
            Jwt decode = this.jwtDecoder.decode(jwt);
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(decode);
            jwtAuthenticationToken.setAuthenticated(true);
            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(jwtAuthenticationToken);
            strategy.setContext(context);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            strategy.clearContext();
        }
    }

    private boolean isUserAlreadyAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    private Optional<Cookie> getJwtCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), JWT_COOKIE_KEY))
                .findFirst();
    }

}
