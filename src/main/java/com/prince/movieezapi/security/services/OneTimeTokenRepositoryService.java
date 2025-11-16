package com.prince.movieezapi.security.services;

import com.prince.movieezapi.security.repositories.OneTimeTokenRepository;
import com.prince.movieezapi.user.models.OneTimeTokenModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OneTimeTokenRepositoryService {
    private final OneTimeTokenRepository oneTimeTokenRepository;

    public OneTimeTokenRepositoryService(OneTimeTokenRepository oneTimeTokenRepository) {
        this.oneTimeTokenRepository = oneTimeTokenRepository;
    }

    public Optional<OneTimeTokenModel> findByToken(String token) {
        return oneTimeTokenRepository.findByTokenValue(token);
    }
}
