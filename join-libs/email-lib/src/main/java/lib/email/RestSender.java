package lib.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ConditionalOnProperty(
        value="lib.email.message-service",
        havingValue = "email",
        matchIfMissing = true)
public class RestSender implements EmailSender {

    @Value("${lib.email.url}") private String url;

    public void send(EmailMessageDTO emailMessageDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(emailMessageDTO, headers);
        restTemplate.postForObject(url, entity, ResponseEntity.class);
    }
}

