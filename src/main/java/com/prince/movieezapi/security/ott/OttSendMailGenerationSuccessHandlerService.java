package com.prince.movieezapi.security.ott;

import com.prince.movieezapi.security.models.OttMailModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Profile("!dev")
@Slf4j
@Service
public class OttSendMailGenerationSuccessHandlerService implements OneTimeTokenGenerationSuccessHandler {

    private final OttMailSenderService ottMailSenderService;

    public OttSendMailGenerationSuccessHandlerService(OttMailSenderService ottMailSenderService) {
        this.ottMailSenderService = ottMailSenderService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        log.info("One time token generated successfully for user: {}", oneTimeToken.getUsername());

        var mailMessage = OttMailModel.builder()
                .recipient(oneTimeToken.getUsername())
                .tokenValue(oneTimeToken.getTokenValue())
                .build();

        try {
            ottMailSenderService.sendMail(mailMessage);
            log.info("Email sent successfully to user: '{}'", oneTimeToken.getUsername());
        } catch (Exception _) {
            log.info("Failed to send email to user: '{}'", oneTimeToken.getUsername());
        }

    }
}
