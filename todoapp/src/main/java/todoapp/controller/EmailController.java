package todoapp.controller;

import lib.email.EmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.service.TodoService;

@RestController
@ConditionalOnProperty(
        value="todoapp.controller.message-service",
        havingValue = "email",
        matchIfMissing = true)
public class EmailController {
    final TodoService todoService;
    final EmailSender emailSender;

    @Value("${email.url}")
    private String emailUrl;

    public EmailController(TodoService todoService, EmailSender emailSender) {
        this.todoService = todoService;
        this.emailSender = emailSender;
    }

    // Email report
    @GetMapping("/report")
    public ResponseEntity<?> report() {
        return emailSender.sendMessage(emailUrl, todoService.genReport());
    }
}
