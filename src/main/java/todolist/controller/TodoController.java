package todolist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import todolist.Status;
import todolist.dto.TodoItemDTO;
import todolist.service.TodoService;
import java.net.URI;
import java.util.List;

@RestController
public class TodoController {
    final TodoService todoService;
    final Logger logger = LoggerFactory.getLogger(TodoController.class);

 private TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET all
    @GetMapping("/todo")
    @ResponseBody
    public ResponseEntity<List<TodoItemDTO>> getTodoList(
            @RequestParam(name = "s", required = false, defaultValue = "new") String status){
     if (status.equalsIgnoreCase("new") || status.equalsIgnoreCase("done")) {
         return new ResponseEntity<>(todoService.findTodoItemByStatus(Status.getFromJson(status))
                 , HttpStatus.OK);
     } else
        throw new IllegalArgumentException("status parameter is invalid, use NEW оr DONE");
    }

    // CREATE
    @PostMapping("/todo")
    public ResponseEntity<TodoItemDTO> createItem(@RequestBody TodoItemDTO todoItemDTO){
        logger.debug(todoItemDTO.toString());
        Integer itemId = todoService.addTodoItem(todoItemDTO);
        UriComponents uriComponents = UriComponentsBuilder.fromPath("todo/{itemId}").buildAndExpand(itemId);
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).build();
    }

    // GET by id
    @GetMapping("/todo/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable Integer itemId){
        return new ResponseEntity<>(todoService.getTodoItemById(itemId), HttpStatus.OK);
    }

    @DeleteMapping("/todo/{itemId}")
    public ResponseEntity<?> detItem(@PathVariable Integer itemId){
        todoService.delTodoItemById(itemId);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    // set status DONE
    @PostMapping("/todo/{itemId}/finish")
    public ResponseEntity<?> finishItem(@PathVariable int itemId){
        todoService.finishTodoItemById(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // UPDATE
    @PutMapping(value="todo/{itemId}")
    public ResponseEntity<?> changeItem(@PathVariable int itemId, @RequestBody TodoItemDTO todoItemDTO){
        logger.debug(todoItemDTO.toString());
        todoService.updateTodoItem(itemId, todoItemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
