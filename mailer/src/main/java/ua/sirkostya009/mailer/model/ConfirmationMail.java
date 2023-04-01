package ua.sirkostya009.mailer.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import ua.sirkostya009.mailer.dto.ConfirmationMailDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "mail")
public class ConfirmationMail {
    @Id
    private String id;
    private String recipient;
    private String token;

    public ConfirmationMailDto toDto() {
        return new ConfirmationMailDto(recipient, token);
    }
}
