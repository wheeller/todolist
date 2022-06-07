package lib.email;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Value("${spring.rabbitmq.host}")
    String host;

    public static final String MESSAGES_EXCHANGE = "MESSAGES.EXCHANGE";
    public static final String ROUTING_KEY_MESSAGES_QUEUE = "ROUTING_KEY_MESSAGES_QUEUE";

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(host);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(connectionFactory());
    }
}
