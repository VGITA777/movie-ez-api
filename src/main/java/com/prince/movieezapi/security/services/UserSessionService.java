package com.prince.movieezapi.security.services;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class UserSessionService {

    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    public UserSessionService(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Map<String, ? extends Session> getSessionsByPrincipalName(UUID uuid) {
        return sessionRepository.findByPrincipalName(uuid.toString());
    }

    public void deleteSessionById(String sessionId, UUID userId) {
        boolean present = sessionRepository.findByPrincipalName(userId.toString())
                .entrySet()
                .stream()
                .anyMatch(e -> e.getKey().equals(sessionId));
        if (!present) {
            throw new IllegalArgumentException("Session not found");
        }
        sessionRepository.deleteById(sessionId);
    }

    public void deleteAllSessionsByPrincipalName(UUID username) {
        sessionRepository.findByPrincipalName(username.toString()).forEach((k, v) -> sessionRepository.deleteById(k));
    }

    public void deleteAllSessionsByPrincipalNameExcludeSessionId(UUID userId, String id) {
        sessionRepository.findByPrincipalName(userId.toString())
                .forEach((k, v) -> {
                    if (k.equals(id)) {
                        return;
                    }
                    sessionRepository.deleteById(k);
                });
    }
}
