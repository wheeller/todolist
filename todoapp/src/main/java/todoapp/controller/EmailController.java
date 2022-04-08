package todoapp.controller;

import lib.email.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.service.TodoService;

@RestController
public class EmailController {
    final TodoService todoService;
    final EmailSender emailSender;
    final RabbitMessagingTemplate rabbitMessagingTemplate;
    final MappingJackson2MessageConverter jackson2Converter;

    @Value("${email.url}")
    private String emailUrl;

    public EmailController(TodoService todoService, EmailSender emailSender, RabbitMessagingTemplate rabbitMessagingTemplate, MappingJackson2MessageConverter jackson2Converter) {
        this.todoService = todoService;
        this.emailSender = emailSender;
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
        this.jackson2Converter = jackson2Converter;
    }

    // Email report
    @GetMapping("/report")
    public ResponseEntity<?> report() {
        return emailSender.sendMessage(emailUrl, todoService.genReport());
    }

    @GetMapping("/report2")
    public ResponseEntity<?> email(){
        rabbitMessagingTemplate.setMessageConverter(this.jackson2Converter);
        rabbitMessagingTemplate.convertAndSend("userQueue", todoService.genReport());
        return new ResponseEntity<>("Report put in queue", HttpStatus.OK);
    }
}
