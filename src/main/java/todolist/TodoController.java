package todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TodoController {
    @Autowired
    TodoApp todoApp;

    @GetMapping("/show-new")
    public ResponseEntity<List<TodoItem>> TodoListNewController(){
        return new ResponseEntity<List<TodoItem>>(todoApp.getTodoList("New"), HttpStatus.OK);
    }
    @GetMapping("/show-all")
    public ResponseEntity<List<TodoItem>> TodoListAllController(){
        return new ResponseEntity<List<TodoItem>>(todoApp.getTodoList(), HttpStatus.OK);
    }
    @GetMapping("/todo-item/{itemId}")
    public ResponseEntity<TodoItem> TodoListGetItem(@PathVariable int itemId){
        return new ResponseEntity<TodoItem>(todoApp.GetTodoItem(itemId), HttpStatus.OK);
    }

    @GetMapping("/del/{itemId}")
    public ResponseEntity TodoListDetItem(@PathVariable int itemId){
        todoApp.DelTodoItem(itemId);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/done/{itemId}")
    public ResponseEntity TodoListStatusItem(@PathVariable int itemId){
        todoApp.StatusChangeTodoItem(itemId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value="new")
    public ResponseEntity<TodoItem> createItem(HttpServletRequest request,
                                               UriComponentsBuilder uriComponentsBuilder){
        var content = request.getParameter("content");
        int itemId = todoApp.AddTodoItem(content);

        UriComponents uriComponents = UriComponentsBuilder.fromPath("todo-item/{itemId}").buildAndExpand(itemId);
        var location = uriComponents.toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value="change/{itemId}")
    public ResponseEntity changeItem(@PathVariable int itemId, HttpServletRequest request){
        var content = request.getParameter("content");
        todoApp.ChangeTodoItem(itemId, content);

        return new ResponseEntity(HttpStatus.OK);
    }
}
