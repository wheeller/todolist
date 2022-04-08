package todoapp.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class RabbitConfiguration {
    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

//    @Bean
//    public AmqpAdmin amqpAdmin(){return new RabbitAdmin(connectionFactory());}

//    @Bean
//    public RabbitTemplate rabbitTemplate(){return new RabbitTemplate(connectionFactory());}

    @Bean
    public Queue userQueue(){return new Queue("userQueue");}

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }
}
