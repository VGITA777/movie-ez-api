package com.prince.movieezapi.security.configs;

import com.prince.movieezapi.security.filters.CustomSecurityHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class SecurityConfigs {

    // TODO: migrate over to sessions since we're using DB for jwt refresh tokens
    @Bean
    @Order(1)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        applyCommonSecuritySettings(http);
        return applyCommonSecuritySettings(http)
                .securityMatcher("/user/**")
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> {
                    csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                    csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
                })
                .authorizeHttpRequests(endpoints -> {
                    endpoints.requestMatchers("/user/auth/**").permitAll();
                    endpoints.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public SecurityFilterChain mediaSecurityFilterChain(HttpSecurity http) throws Exception {
        return applyCommonSecuritySettings(http)
                .securityMatcher("/media/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(endpoints -> endpoints.anyRequest().authenticated())
                .addFilterBefore(new CustomSecurityHeaderFilter(), AuthorizationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private HttpSecurity applyCommonSecuritySettings(HttpSecurity http) throws Exception {
        return http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
    }

}
