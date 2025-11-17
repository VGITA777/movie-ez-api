package com.prince.movieezapi.security.ott;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prince.movieezapi.shared.models.responses.ServerAuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Profile("dev")
@Slf4j
@Service
public class OttGenerationSuccessHandlerService implements OneTimeTokenGenerationSuccessHandler {

    private final ObjectMapper objectMapper;

    public OttGenerationSuccessHandlerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException {
        log.info("One time token generated successfully for user: {}", oneTimeToken.getUsername());
        var writer = response.getWriter();
        var message = objectMapper.writeValueAsString(ServerAuthenticationResponse.success("One time token generated successfully", oneTimeToken));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        writer.write(message);
        writer.flush();
        writer.close();
    }
}
