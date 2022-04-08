package email;

import lib.email.EmailMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service(value = "email")
public class EmailService {
    @Value("${spring.mail.username}")
    String username;

    private final JavaMailSender emailSender;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage (EmailMessageDTO messageDTO) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(messageDTO.getTo());
        message.setSubject(messageDTO.getSubject());
        message.setText(messageDTO.getText());
        emailSender.send(message);
    }
}
