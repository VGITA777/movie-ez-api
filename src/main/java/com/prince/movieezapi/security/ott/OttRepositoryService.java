package com.prince.movieezapi.security.ott;

import com.prince.movieezapi.security.repositories.OttRepository;
import com.prince.movieezapi.user.models.OneTimeTokenModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OttRepositoryService {
    private final OttRepository ottRepository;

    public OttRepositoryService(OttRepository ottRepository) {
        this.ottRepository = ottRepository;
    }

    public Optional<OneTimeTokenModel> findByToken(String token) {
        return ottRepository.findByTokenValue(token);
    }
}
