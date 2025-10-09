package com.prince.movieezapi.security.services;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserSessionService {

    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    public UserSessionService(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Map<String, ? extends Session> getSessionsByUser(String username) {
        return sessionRepository.findByPrincipalName(username);
    }

    public void deleteSessionById(String id, String username) {
        boolean present = sessionRepository.findByPrincipalName(username)
                .entrySet()
                .stream()
                .anyMatch(e -> e.getKey().equals(id));
        if (!present) {
            throw new IllegalArgumentException("Session not found");
        }
        sessionRepository.deleteById(id);
    }

    public void deleteAllSessionsByUsername(String username) {
        sessionRepository.findByPrincipalName(username).forEach((k, v) -> sessionRepository.deleteById(k));
    }

    public void deleteAllSessionsByUsernameExcludeSessionId(String username, String id) {
        sessionRepository.findByPrincipalName(username)
                .forEach((k, v) -> {
                    if (k.equals(id)) {
                        return;
                    }
                    sessionRepository.deleteById(k);
                });
    }
}
