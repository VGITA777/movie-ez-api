package com.prince.movieezapi.security.models;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "security.rsa")
public record RsaKeyPair(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
