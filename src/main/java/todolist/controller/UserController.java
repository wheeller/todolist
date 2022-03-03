package todolist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import todolist.dto.UserDTO;
import todolist.service.UserService;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    // GET all
    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getUserList(){
            return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    // CREATE
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        Integer id = userService.addUser(userDTO);
        UriComponents uriComponents = UriComponentsBuilder.fromPath("user/{id}").buildAndExpand(id);
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> removeUser(@PathVariable Integer id){
        userService.delUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
