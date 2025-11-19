package com.prince.movieezapi.security.ott;

import com.prince.movieezapi.security.models.OttMailModel;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OttMailSenderService {

    private static final String OTT_VERIFY_PATH = "/user/auth/login/ott";

    private final JavaMailSender mailSender;

    @Value("${app.movieez.security.auth.ott.from-main:no-reply@security.movieez.com}")
    private String fromEmail;

    @Value("${app.security.ott.token-expiration-minutes}")
    private long tokenExpirationMinutes;

    @Value("${app.base-url}")
    private String baseUrl;

    public OttMailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends an email containing a one-time token link to the user.
     *
     * @param mailModel the {@link OttMailModel} containing the recipient email and token value.
     * @return true if the email was sent successfully, false otherwise.
     * @throws Exception if there is an error sending the email.
     */
    public boolean sendMail(OttMailModel mailModel) throws Exception {

        var userAddress = new InternetAddress(mailModel.recipient());

        var message = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                var message = new StringBuilder();
                var loginUrl = baseUrl + OTT_VERIFY_PATH + "?token=" + mailModel.tokenValue();

                message.append("Hello ")
                        .append(mailModel.recipient())
                        .append(",\n\n")
                        .append("Use the link below to login to your account:\n")
                        .append(loginUrl)
                        .append("\n\n")
                        .append("The link will expire in ")
                        .append(tokenExpirationMinutes)
                        .append(" minutes. If you did not request this, please ignore this email.");

                mimeMessage.setFrom(fromEmail);
                mimeMessage.setSubject("Login to your account");
                mimeMessage.setRecipient(Message.RecipientType.TO, userAddress);
                mimeMessage.setText(message.toString());
            }
        };

        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email to user: '{}'", mailModel.recipient(), e);
            return false;
        }

        return true;
    }
}
