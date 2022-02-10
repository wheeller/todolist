package todolist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TodoController {
    final TodoService todoService;

 private TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET all
    @GetMapping("/todo")
    @ResponseBody
    public ResponseEntity<List<TodoItemDTO>> getTodoList(HttpServletRequest request){
        Status status = null;
        String statusParam = request.getParameter("s");

        if (statusParam == null || statusParam.equalsIgnoreCase("new"))
            status = Status.NEW;
        else if (statusParam.equalsIgnoreCase("done"))
            status = Status.DONE;

        return new ResponseEntity<>(todoService.findTodoItemByStatus(status)
                ,HttpStatus.OK);
    }

    // CREATE
    @PostMapping("/todo")
    public ResponseEntity<TodoItemDTO> createItem(@RequestBody TodoItemDTO todoItemDTO){
        Integer itemId = todoService.addTodoItem(todoItemDTO);
        UriComponents uriComponents = UriComponentsBuilder.fromPath("todo/{itemId}").buildAndExpand(itemId);
        var location = uriComponents.toUri();

        return ResponseEntity.created(location).build();
    }

    // GET by id
    @GetMapping("/todo/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable Integer itemId){
        return new ResponseEntity<>(todoService.getTodoItemById(itemId), HttpStatus.OK);
    }

    @DeleteMapping("/todo/{itemId}")
    public ResponseEntity<?> detItem(@PathVariable Integer itemId){
        if (todoService.delTodoItemById(itemId))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // set status DONE
    @PostMapping("/todo/{itemId}/finish")
    public ResponseEntity<?> finishItem(@PathVariable int itemId){
        if (todoService.finishTodoItemById(itemId))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // UPDATE
    @PutMapping(value="todo/{itemId}")
    public ResponseEntity<?> changeItem(@PathVariable int itemId, @RequestBody TodoItemDTO todoItemDTO){
        if (todoService.updateTodoItem(itemId, todoItemDTO))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
