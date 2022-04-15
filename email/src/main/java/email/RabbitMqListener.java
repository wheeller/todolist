package email;

import lib.email.EmailMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;


@EnableRabbit
@Component
public class RabbitMqListener {
    final EmailService emailService;
    Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    public RabbitMqListener(EmailService emailService) {
        this.emailService = emailService;

    }

    @RabbitListener(queues = "userQueue")
    public void processUserQueue(EmailMessageDTO message) {
        try {
            logger.info("Received message from userQueue {}", message);
            emailService.sendSimpleMessage(message);
        } catch (RuntimeException ex) {
            // against infinite listener restarts
            throw new ImmediateAcknowledgeAmqpException(ex.getMessage());
        }
    }
}