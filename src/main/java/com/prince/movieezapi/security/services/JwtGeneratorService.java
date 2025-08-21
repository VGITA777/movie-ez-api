package com.prince.movieezapi.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtGeneratorService {

    private final NimbusJwtEncoder encoder;
    @Value("${security.rsa.jwt.expiration:3600}") // Default to 1 hour if not set
    private long expirationTimeSeconds;

    public JwtGeneratorService(NimbusJwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String genToken(Authentication authentication) {
        log.info("Generating JWT Token for user: {}", authentication.getName());
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expirationTimeSeconds))
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        return encoder.encode(jwtEncoderParameters).getTokenValue();
    }
}
