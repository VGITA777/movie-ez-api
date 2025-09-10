package com.prince.movieezapi.shared.utilities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

public class SecurityUtils {
    private static final SecurityContextHolderStrategy CONTEXT_HOLDER_STRATEGY = SecurityContextHolder.getContextHolderStrategy();

    public static void clearAuthentication() {
        CONTEXT_HOLDER_STRATEGY.clearContext();
    }

    public static boolean isAuthenticated() {
        SecurityContext context = CONTEXT_HOLDER_STRATEGY.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static void setCurrentAuthentication(Authentication authenticated, HttpSessionSecurityContextRepository securityContextRepository, HttpServletRequest request, HttpServletResponse response) {
        SecurityContext emptyContext = CONTEXT_HOLDER_STRATEGY.createEmptyContext();
        emptyContext.setAuthentication(authenticated);
        CONTEXT_HOLDER_STRATEGY.setContext(emptyContext);
        securityContextRepository.saveContext(emptyContext, request, response);
    }
}
