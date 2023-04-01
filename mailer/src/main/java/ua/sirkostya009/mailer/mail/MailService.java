package ua.sirkostya009.mailer.mail;

import ua.sirkostya009.mailer.dto.ConfirmationMailDto;

public interface MailService {
    boolean send(ConfirmationMailDto mail);
}
