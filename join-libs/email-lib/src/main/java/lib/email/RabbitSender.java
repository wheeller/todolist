package lib.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static lib.email.RabbitConfiguration.MESSAGES_EXCHANGE;
import static lib.email.RabbitConfiguration.ROUTING_KEY_MESSAGES_QUEUE;

@Service
@ConditionalOnProperty(
        value="lib.email.message-service",
        havingValue = "rabbit")
public class RabbitSender implements EmailSender{

    final RabbitTemplate rabbitTemplate;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseEntity<?> send(EmailMessageDTO messageDTO){
        SimpleMessageConverter converter = new SimpleMessageConverter();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("To", messageDTO.getTo());
        messageProperties.setHeader("Subject", messageDTO.getSubject());
        messageProperties.setContentEncoding("UTF-8");
        Message message = converter.toMessage(messageDTO.getText(), messageProperties);

        rabbitTemplate.convertAndSend(MESSAGES_EXCHANGE, ROUTING_KEY_MESSAGES_QUEUE, message);
        logger.info("Message send {}", message);
        return new ResponseEntity<>("Report put in queue", HttpStatus.OK);
    }
}
