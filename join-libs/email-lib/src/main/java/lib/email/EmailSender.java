package lib.email;

import org.springframework.http.ResponseEntity;

public interface EmailSender {
    public ResponseEntity<?> send(EmailMessageDTO emailMessageDTO);
}