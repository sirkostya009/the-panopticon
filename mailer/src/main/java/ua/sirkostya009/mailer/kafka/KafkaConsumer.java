package ua.sirkostya009.mailer.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ua.sirkostya009.mailer.dto.ConfirmationMailDto;
import ua.sirkostya009.mailer.mail.MailService;
import ua.sirkostya009.mailer.model.ConfirmationMail;
import ua.sirkostya009.mailer.repository.ConfirmationMailRepository;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final MailService service;
    private final ConfirmationMailRepository repository;

    @KafkaListener(topics = "${kafka.topics.mail-topic}")
    public void consume(ConfirmationMailDto dto) {
        if (!service.send(dto))
            repository.save(ConfirmationMail.builder()
                    .recipient(dto.email())
                    .token(dto.token())
                    .build());
    }
}
