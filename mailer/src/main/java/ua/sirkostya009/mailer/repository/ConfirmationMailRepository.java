package ua.sirkostya009.mailer.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.sirkostya009.mailer.model.ConfirmationMail;

public interface ConfirmationMailRepository extends ElasticsearchRepository<ConfirmationMail, String> {
}
