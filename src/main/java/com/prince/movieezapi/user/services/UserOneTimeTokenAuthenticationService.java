package com.prince.movieezapi.user.services;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ott.GenerateOneTimeTokenRequest;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.stereotype.Service;

@Service
public class UserOneTimeTokenAuthenticationService {

  @Value("${app.security.ott.token-expiration-minutes}")
  private long tokenExpirationMinutes;

  private final OneTimeTokenService oneTimeTokenService;

  public UserOneTimeTokenAuthenticationService(OneTimeTokenService oneTimeTokenService) {
    this.oneTimeTokenService = oneTimeTokenService;
  }

  public OneTimeToken generateToken(String username) {
    GenerateOneTimeTokenRequest ottRequest = new GenerateOneTimeTokenRequest(
        username,
                                                                             Duration.ofMinutes(tokenExpirationMinutes)
    );
    return oneTimeTokenService.generate(ottRequest);
  }

}
