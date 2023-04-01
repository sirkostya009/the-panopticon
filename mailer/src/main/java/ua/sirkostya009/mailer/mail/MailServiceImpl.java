package ua.sirkostya009.mailer.mail;

import jakarta.mail.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.sirkostya009.mailer.dto.ConfirmationMailDto;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender sender;

    @Value("${server.email}")
    private String domainAddress;
    @Value("${server.registration-url}")
    private String registrationUrl;

    @Override
    public boolean send(ConfirmationMailDto mail) {
        var message = sender.createMimeMessage();

        try {
            message.setFrom(domainAddress);
            message.setRecipients(Message.RecipientType.TO, mail.email());
            message.setSubject("Confirm your email address");
            message.setContent(buildBody(mail.token()), "text/html");

            sender.send(message);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // TODO: make a cool html page here
    private String buildBody(String token) {
        return registrationUrl + token;
    }
}
