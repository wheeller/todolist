package email;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfiguration {

//    DLQ (Dead Letter Queue)
    public static final String DLX_MESSAGES_EXCHANGE = "DLX.MESSAGES.EXCHANGE";
    public static final String DLQ_MESSAGES_QUEUE = "DLQ.MESSAGES.QUEUE";
    public static final String MESSAGES_QUEUE = "MESSAGES.QUEUE";
    public static final String MESSAGES_EXCHANGE = "MESSAGES.EXCHANGE";
    public static final String ROUTING_KEY_MESSAGES_QUEUE = "ROUTING_KEY_MESSAGES_QUEUE";

    @Bean
    Queue messagesQueue() {
        return QueueBuilder.durable(MESSAGES_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_MESSAGES_EXCHANGE)
                .build();
    }

    @Bean
    DirectExchange messagesExchange() {
        return new DirectExchange(MESSAGES_EXCHANGE);
    }

    @Bean
    Binding bindingMessages() {
        return BindingBuilder.bind(messagesQueue()).to(messagesExchange()).with(ROUTING_KEY_MESSAGES_QUEUE);
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DLX_MESSAGES_EXCHANGE);
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ_MESSAGES_QUEUE).build();
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }
}
