package email;

import lib.email.EmailMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Component;

import static email.RabbitConfiguration.DLQ_MESSAGES_QUEUE;
import static email.RabbitConfiguration.MESSAGES_QUEUE;


@EnableRabbit
@Component
public class RabbitMqListener {
    final EmailService emailService;
    Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    public RabbitMqListener(EmailService emailService) {
        this.emailService = emailService;

    }

    @RabbitListener(queues = MESSAGES_QUEUE)
    public void receiveMessage(Message message) {
        logger.info("Message: " + message.toString());
//        throw new RuntimeException("fail"); // debug

        SimpleMessageConverter converter = new SimpleMessageConverter();

        EmailMessageDTO emailMessageDTO = new EmailMessageDTO(
                message.getMessageProperties().getHeader("To"),
                message.getMessageProperties().getHeader("Subject"),
                converter.fromMessage(message).toString()
            );
        logger.info("Create EmailMessageDTO {}", emailMessageDTO);
        emailService.sendSimpleMessage(emailMessageDTO);
//        try {
//            emailService.sendSimpleMessage(emailMessageDTO);
//        } catch (MailAuthenticationException ex) {
//                    logger.info("FAIL");
//                   // against infinite listener restarts
////                  throw new ImmediateAcknowledgeAmqpException(ex.getMessage());
//                    throw new AmqpRejectAndDontRequeueException(ex.getMessage());
//                }
}

    @RabbitListener(queues = DLQ_MESSAGES_QUEUE)
    public void processFailedMessages(Message message) {
        logger.info("Received failed message: " + message.toString());
    }
}