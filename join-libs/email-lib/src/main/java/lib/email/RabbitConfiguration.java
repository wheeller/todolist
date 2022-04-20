package lib.email;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    public static final String MESSAGES_EXCHANGE = "MESSAGES.EXCHANGE";
    public static final String ROUTING_KEY_MESSAGES_QUEUE = "ROUTING_KEY_MESSAGES_QUEUE";

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }
}
