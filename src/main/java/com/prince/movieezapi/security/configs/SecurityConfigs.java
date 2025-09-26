package com.prince.movieezapi.security.configs;

import com.prince.movieezapi.security.authprovider.MovieEzEmailAuthenticationProvider;
import com.prince.movieezapi.security.authprovider.MovieEzUsernameAuthenticationProvider;
import com.prince.movieezapi.security.filters.CustomSecurityHeaderFilter;
import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.shared.utilities.BasicUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Configuration
@EnableWebSecurity
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class SecurityConfigs {

    @Value("${movieez.security.header:WowThisGottaBeTheBestSecurityMeasure101}")
    private String mediaSecurityHeader;

    @Bean
    @Order(1)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        applyCommonSecuritySettings(http);
        return applyCommonSecuritySettings(http)
                .securityMatcher("/user/**")
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(csrf -> {
                    csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                    csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
                })
                .logout(logout -> {
                    logout.logoutUrl("/user/auth/logout");
                    logout.deleteCookies("XSRF-TOKEN", "SESSION");
                    logout.invalidateHttpSession(true);
                    logout.clearAuthentication(true);
                    logout.logoutSuccessHandler((request, response, authentication) -> {
                        BasicUtils.sendJson(HttpStatus.OK, ServerGenericResponse.success("Successfully logged out", null), response);
                    });
                    logout.permitAll();
                })
                .authorizeHttpRequests(endpoints -> {
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

    private HttpSecurity applyCommonSecuritySettings(HttpSecurity http) throws Exception {
        return http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
    }

}
