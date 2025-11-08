package com.prince.movieezapi.security.configs;

import com.prince.movieezapi.security.authenticationtokens.MovieEzFullyAuthenticatedUser;
import com.prince.movieezapi.security.authprovider.MovieEzEmailAuthenticationProvider;
import com.prince.movieezapi.security.authprovider.MovieEzUsernameAuthenticationProvider;
import com.prince.movieezapi.security.filters.CustomSecurityHeaderFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

import java.util.List;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class SecurityConfigs {

    @Value("${app.movieez.security.header:WowThisGottaBeTheBestSecurityMeasure101}")
    private String mediaSecurityHeader;

    @Bean
    @Order(1)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        applyCommonSecuritySettings(http);
        return applyCommonSecuritySettings(http)
                .securityMatcher("/user/**")
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(csrf -> {
                    csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                    csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
                })
                .authorizeHttpRequests(endpoints -> {
                    endpoints.requestMatchers("/user/auth/logout")
                            .access(new UserFullyAuthenticatedAuthorizationManager());
                    endpoints.requestMatchers("/user/auth/**").permitAll();
                    endpoints.requestMatchers("/user/register/**").permitAll();
                    endpoints.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public SecurityFilterChain mediaSecurityFilterChain(HttpSecurity http) throws Exception {
        return applyCommonSecuritySettings(http)
                .securityMatcher("/media/**")
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(endpoints -> endpoints.anyRequest().authenticated())
                .addFilterBefore(new CustomSecurityHeaderFilter(mediaSecurityHeader), AuthorizationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            @Lazy MovieEzUsernameAuthenticationProvider movieEzUsernameAuthenticationProvider,
            @Lazy MovieEzEmailAuthenticationProvider movieEzEmailAuthenticationProvider
    ) {
        return new ProviderManager(List.of(
                movieEzUsernameAuthenticationProvider,
                movieEzEmailAuthenticationProvider
        ));
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

    private HttpSecurity applyCommonSecuritySettings(HttpSecurity http) throws Exception {
        return http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
    }

    private static class UserFullyAuthenticatedAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
        @Override
        public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
            if (authentication.get() instanceof MovieEzFullyAuthenticatedUser) {
                return new AuthorizationDecision(true);
            }
            return new AuthorizationDecision(false);
        }
    }
}
