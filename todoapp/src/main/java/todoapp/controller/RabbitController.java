package todoapp.controller;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.service.TodoService;

@RestController
@ConditionalOnProperty(
        value="todoapp.controller.message-service",
        havingValue = "rabbit")
public class RabbitController {
    final TodoService todoService;
    final RabbitMessagingTemplate rabbitMessagingTemplate;
    final MappingJackson2MessageConverter jackson2Converter;

    public RabbitController(TodoService todoService, RabbitMessagingTemplate rabbitMessagingTemplate,
                            MappingJackson2MessageConverter jackson2Converter) {
        this.todoService = todoService;
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
        this.jackson2Converter = jackson2Converter;
    }

    @GetMapping("/report")
    public ResponseEntity<?> email(){
        rabbitMessagingTemplate.setMessageConverter(this.jackson2Converter);
        rabbitMessagingTemplate.convertAndSend("userQueue", todoService.genReport());
        return new ResponseEntity<>("Report put in queue", HttpStatus.OK);
    }
}
