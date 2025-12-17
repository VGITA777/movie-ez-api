package com.prince.movieezapi.security.configs;

import com.prince.movieezapi.security.authenticationtokens.MovieEzFullyAuthenticatedUser;
import com.prince.movieezapi.security.authprovider.MovieEzEmailAuthenticationProvider;
import com.prince.movieezapi.security.authprovider.MovieEzOneTimeTokenAuthenticationProvider;
import com.prince.movieezapi.security.authprovider.MovieEzUsernameAuthenticationProvider;
import com.prince.movieezapi.security.filters.CustomSecurityHeaderFilter;
import com.prince.movieezapi.security.ratelimit.RateLimiterFilter;
import com.prince.movieezapi.security.ratelimit.RateLimiterFilterImpl;
import com.prince.movieezapi.security.ratelimit.RateLimiterService;
import com.prince.movieezapi.shared.models.responses.ServerAuthenticationResponse;
import com.prince.movieezapi.shared.utilities.BasicUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ott.JdbcOneTimeTokenService;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.ott.OneTimeTokenLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class SecurityConfigs {

    @Value("${app.movieez.security.header:#{null}}")
    private String mediaSecurityHeader;

    /**
     * Security filter chain for the /user/** endpoint.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain userSecurityFilterChain(
            HttpSecurity http,
            MovieEzOneTimeTokenAuthenticationProvider ottAuthProvider,
            ObjectMapper objectMapper,
            RateLimiterFilter rateLimiterFilter
    ) throws Exception {
        return applyCommonSecuritySettings(http, rateLimiterFilter)
                .securityMatcher("/user/**")
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .csrf(SecurityConfigs::configureCsrf)
                .oneTimeTokenLogin(ott -> configureUserOneTimeTokenLogin(ott, ottAuthProvider, objectMapper))
                .exceptionHandling(ex -> configureUserExceptionHandling(ex, objectMapper))
                .authorizeHttpRequests(endpoints -> {
                    endpoints.requestMatchers("/user/auth/logout").access(new UserFullyAuthenticatedAuthorizationManager());
                    endpoints.requestMatchers("/user/auth/**").permitAll();
                    endpoints.requestMatchers("/user/register/**").permitAll();
                    endpoints.anyRequest().authenticated();
                })
                .build();
    }

    /**
     * Security filter chain for the /media/** endpoint.
     */
    @Bean
    public SecurityFilterChain mediaSecurityFilterChain(HttpSecurity http,
                                                        BasicUtils basicUtils,
                                                        RateLimiterFilter rateLimiterFilter) throws Exception {
        return applyCommonSecuritySettings(http, rateLimiterFilter)
                .securityMatcher("/media/**")
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(endpoints -> endpoints.anyRequest().permitAll())
                .addFilterBefore(new CustomSecurityHeaderFilter(mediaSecurityHeader, basicUtils), AuthorizationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(@Lazy MovieEzUsernameAuthenticationProvider movieEzUsernameAuthenticationProvider, @Lazy MovieEzEmailAuthenticationProvider movieEzEmailAuthenticationProvider) {
        return new ProviderManager(List.of(movieEzUsernameAuthenticationProvider, movieEzEmailAuthenticationProvider));
    }

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler(@Lazy HttpSessionSecurityContextRepository httpSessionSecurityContextRepository) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.setClearAuthentication(true);
        securityContextLogoutHandler.setInvalidateHttpSession(true);
        securityContextLogoutHandler.setSecurityContextRepository(httpSessionSecurityContextRepository);
        return securityContextLogoutHandler;
    }

    @Bean
    public OneTimeTokenService oneTimeTokenService(JdbcOperations jdbcOperations) {
        return new JdbcOneTimeTokenService(jdbcOperations);
    }

    @Bean
    public RateLimiterFilter rateLimiterFilter(RateLimiterService rateLimiterService) {
        return new RateLimiterFilterImpl(rateLimiterService);
    }

    private HttpSecurity applyCommonSecuritySettings(HttpSecurity http, RateLimiterFilter filter) throws Exception {
        return http.httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterAfter(filter, AuthorizationFilter.class);
    }

    private static void configureCsrf(CsrfConfigurer<HttpSecurity> csrf) {
        csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
    }

    private static void configureUserOneTimeTokenLogin(OneTimeTokenLoginConfigurer<HttpSecurity> ott, AuthenticationProvider ottAuthProvider, ObjectMapper objectMapper) {
        ott.tokenGeneratingUrl("/user/auth/request/ott");
        ott.loginProcessingUrl("/user/auth/login/ott");
        ott.authenticationProvider(ottAuthProvider);
        ott.defaultSuccessUrl("/", false);
        ott.showDefaultSubmitPage(false);
        ott.successHandler((_, response, _) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            var body = objectMapper.writeValueAsString(
                    ServerAuthenticationResponse.success("Login successful", null)
            );
            response.getWriter().write(body);
        });
    }

    private static void configureUserExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> ex, ObjectMapper objectMapper) {
        ex.authenticationEntryPoint((_, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            var body = objectMapper.writeValueAsString(
                    ServerAuthenticationResponse.failure("Unauthorized", authException.getMessage())
            );
            response.getWriter().write(body);
        });

        ex.accessDeniedHandler((_, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            var body = objectMapper.writeValueAsString(
                    ServerAuthenticationResponse.failure("Access Denied", accessDeniedException.getMessage())
            );
            response.getWriter().write(body);
        });
    }

    private static class UserFullyAuthenticatedAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
        @Override
        public @Nullable AuthorizationResult authorize(Supplier<? extends @Nullable Authentication> authentication, RequestAuthorizationContext object) {
            if (authentication.get() instanceof MovieEzFullyAuthenticatedUser) {
                return new AuthorizationDecision(true);
            }
            return new AuthorizationDecision(false);
        }
    }
}
