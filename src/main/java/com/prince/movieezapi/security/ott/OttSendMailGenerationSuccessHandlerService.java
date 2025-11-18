package com.prince.movieezapi.security.ott;

import com.prince.movieezapi.security.models.OttMailModel;
import com.prince.movieezapi.shared.models.responses.ServerAuthenticationResponse;
import com.prince.movieezapi.shared.utilities.BasicUtils;
import com.prince.movieezapi.user.services.MovieEzUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Profile("!dev")
@Slf4j
@Service
public class OttSendMailGenerationSuccessHandlerService implements OneTimeTokenGenerationSuccessHandler {

    private final OttMailSenderService ottMailSenderService;
    private final MovieEzUserService userService;
    private final BasicUtils basicUtils;

    public OttSendMailGenerationSuccessHandlerService(OttMailSenderService ottMailSenderService, MovieEzUserService userService, BasicUtils basicUtils) {
        this.ottMailSenderService = ottMailSenderService;
        this.userService = userService;
        this.basicUtils = basicUtils;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        var userIdentifier = oneTimeToken.getUsername();

        if (!basicUtils.isValidEmail(userIdentifier)) {
            var user = userService.findByUsername(userIdentifier);
            if (user.isEmpty()) {
                return;
            }
            userIdentifier = user.get().getEmail();
        }

        log.info("One time token generated successfully for user: {}", userIdentifier);

        var mailMessage = OttMailModel.builder()
                .recipient(userIdentifier)
                .tokenValue(oneTimeToken.getTokenValue())
                .build();

        try {
            ottMailSenderService.sendMail(mailMessage);
            basicUtils.sendJson(HttpStatus.OK, ServerAuthenticationResponse.success("Email sent successfully", null), response);
            log.info("Email sent successfully to user: '{}'", userIdentifier);
        } catch (Exception _) {
            log.info("Failed to send email to user: '{}'", userIdentifier);
        }

    }
}
