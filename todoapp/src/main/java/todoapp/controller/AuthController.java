package todoapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import todoapp.dto.UserDTO;
import todoapp.service.UserService;


@RestController
public class AuthController {

    private final UserService userService;
    final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping(path = "/auth",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> authorize(@RequestBody UserDTO userDTO){
        userDTO.setId(userService.getUserByName(userDTO.getName()).getId());
        String token = userService.authorize(userDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}