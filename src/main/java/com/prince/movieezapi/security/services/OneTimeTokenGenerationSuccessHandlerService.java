package com.prince.movieezapi.security.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prince.movieezapi.shared.models.responses.ServerAuthenticationResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class OneTimeTokenGenerationSuccessHandlerService implements OneTimeTokenGenerationSuccessHandler {

    private final ObjectMapper objectMapper;

    public OneTimeTokenGenerationSuccessHandlerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        // TODO: Send email to user with one time token
        log.info("One time token generated successfully for user: {}", oneTimeToken.getUsername());
        val writer = response.getWriter();
        val message = objectMapper.writeValueAsString(ServerAuthenticationResponse.success("One time token generated successfully", oneTimeToken));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        writer.write(message);
        writer.flush();
        writer.close();
    }
}
