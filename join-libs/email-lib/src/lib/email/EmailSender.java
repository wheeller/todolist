package lib.email;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailSender {

    public ResponseEntity<?> sendMessage(String emailUrl, EmailMessageDTO emailMessageDTO){
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> entity = new HttpEntity<>(emailMessageDTO, headers);
        return restTemplate.postForObject(emailUrl, entity, ResponseEntity.class);
    }
}
