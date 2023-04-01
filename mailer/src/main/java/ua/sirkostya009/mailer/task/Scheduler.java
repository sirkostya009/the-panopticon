package ua.sirkostya009.mailer.task;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.sirkostya009.mailer.mail.MailService;
import ua.sirkostya009.mailer.model.ConfirmationMail;
import ua.sirkostya009.mailer.repository.ConfirmationMailRepository;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final MailService service;
    private final ConfirmationMailRepository repository;

    @Value("${schedule.mail.sweep-size}")
    private int size;

    @Scheduled(
            fixedRateString = "${schedule.mail.fixed-rate}",
            fixedDelayString = "${schedule.mail.fixed-delay}"
    )
    public void sweep() {
        var page = 0;
        Page<ConfirmationMail> confirmationMails;

        do {
            confirmationMails = repository.findAll(PageRequest.of(page++, size));

            confirmationMails.forEach(mail -> {
                if (service.send(mail.toDto())) repository.delete(mail);
            });
        } while (confirmationMails.hasNext());
    }
}
