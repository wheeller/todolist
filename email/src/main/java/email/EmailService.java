package email;

import lib.email.EmailMessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service(value = "email")
public class EmailService {
    @Value("${spring.mail.username}")
    String username;

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(EmailMessageDTO messageDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(messageDTO.getTo());
        message.setSubject(messageDTO.getSubject());
        message.setText(messageDTO.getText());
        emailSender.send(message);
    }
}
