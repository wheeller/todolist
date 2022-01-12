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

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    public ResponseEntity<List<TodoItem>> getList(HttpServletRequest request){
        TodoItem.Status status = null;
        String statusParam = request.getParameter("s");

        if (statusParam == null || statusParam.equalsIgnoreCase("new"))
            status = TodoItem.Status.NEW;
        else if (statusParam.equalsIgnoreCase("done"))
            status = TodoItem.Status.DONE;
//        else if (statusParam.equalsIgnoreCase("all"))
//            status = null;

        return new ResponseEntity<>(todoService.getList(status), HttpStatus.OK);
    }
    @GetMapping("/todo/{itemId}")
    public ResponseEntity<TodoItem> getItem(@PathVariable int itemId){
        return new ResponseEntity<>(todoService.get(itemId), HttpStatus.OK);
    }

    @DeleteMapping("/todo/{itemId}")
    public ResponseEntity<?> detItem(@PathVariable int itemId){
        todoService.del(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/todo/{itemId}/finish")
    public ResponseEntity<?> finishItem(@PathVariable int itemId){
        todoService.finish(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="/todo")
    public ResponseEntity<TodoItem> createItem(@RequestBody TodoItem todoItem){
        int itemId = todoService.add(todoItem.getContent());

        UriComponents uriComponents = UriComponentsBuilder.fromPath("todo/{itemId}").buildAndExpand(itemId);
        var location = uriComponents.toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value="todo/{itemId}")
    public ResponseEntity<?> changeItem(@PathVariable int itemId, @RequestBody TodoItem todoItem){
        todoService.modify(itemId, todoItem.getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
