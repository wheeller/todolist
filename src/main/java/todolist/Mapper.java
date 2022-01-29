package todolist;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class Mapper {
    public TodoItemDTO toDto(TodoItem todoItem){
        int id = todoItem.getId();
        String content = todoItem.getContent();
        Status status = todoItem.getStatus();
        OffsetDateTime createDateTime = todoItem.getCreateDateTime();

        return new TodoItemDTO(id, content, status, createDateTime);
    }

    public TodoItem fromDto(TodoItemDTO todoItemDTO){
        return new TodoItem(todoItemDTO.getContent());
    }
}
