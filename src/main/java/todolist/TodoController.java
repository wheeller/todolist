package todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TodoController {
    final TodoService todoService;

    private TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Autowired
    private Mapper mapper;

    @GetMapping("/todo")
    @ResponseBody
    public ResponseEntity<List<TodoItemDTO>> getTodoList(HttpServletRequest request){
        Status status = null;
        String statusParam = request.getParameter("s");

        if (statusParam == null || statusParam.equalsIgnoreCase("new"))
            status = Status.NEW;
        else if (statusParam.equalsIgnoreCase("done"))
            status = Status.DONE;
//        else if (statusParam.equalsIgnoreCase("all"))
//            status = null;

        return new ResponseEntity<>(todoService.getTodoList(status)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList())
                    ,HttpStatus.OK);
    }

    @PostMapping("/todo")
    public ResponseEntity<TodoItemDTO> createItem(@RequestBody TodoItemDTO todoItemDTO){
        int itemId = todoService.add(todoItemDTO.getContent());

        UriComponents uriComponents = UriComponentsBuilder.fromPath("todo/{itemId}").buildAndExpand(itemId);
        var location = uriComponents.toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/todo/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable int itemId){
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


    @PutMapping(value="todo/{itemId}")
    public ResponseEntity<?> changeItem(@PathVariable int itemId, @RequestBody TodoItemDTO todoItemDTO){
        todoService.modify(itemId, mapper.fromDto(todoItemDTO).getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
