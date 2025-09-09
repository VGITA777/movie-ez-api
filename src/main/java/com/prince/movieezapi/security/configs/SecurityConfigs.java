package com.prince.movieezapi.security.configs;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import com.prince.movieezapi.security.filters.CustomSecurityHeaderFilter;
import com.prince.movieezapi.security.filters.JwtCookieAuthenticationFilter;
import com.prince.movieezapi.security.models.RsaKeyPair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class SecurityConfigs {

    private final JwtCookieAuthenticationFilter jwtCookieAuthenticationFilter;

    public SecurityConfigs(@Lazy JwtCookieAuthenticationFilter jwtCookieAuthenticationFilter) {
        this.jwtCookieAuthenticationFilter = jwtCookieAuthenticationFilter;
    }

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
                .oauth2ResourceServer(oauth2ResourceServerConfigurer -> oauth2ResourceServerConfigurer.jwt(Customizer.withDefaults()))
                .addFilterBefore(jwtCookieAuthenticationFilter, AuthorizationFilter.class)
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
    public NimbusJwtDecoder nimbusJwtDecoder(RsaKeyPair rsaKeyPair) {
        return NimbusJwtDecoder.withPublicKey(rsaKeyPair.publicKey()).build();
    }

    @Bean
    public NimbusJwtEncoder nimbusJwtEncoder(RsaKeyPair rsaKeyPair) {
        RSAKey rsaKey = new RSAKey.Builder(rsaKeyPair.publicKey()).privateKey(rsaKeyPair.privateKey()).build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        ImmutableJWKSet<SecurityContext> securityContextImmutableJWKSet = new ImmutableJWKSet<>(jwkSet);
        return new NimbusJwtEncoder(securityContextImmutableJWKSet);
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
