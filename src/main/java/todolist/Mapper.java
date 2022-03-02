package todolist;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class Mapper {
    // TodoItem
    public TodoItemDTO toDto(TodoItem todoItem){
        int id = todoItem.getId();
        String content = todoItem.getContent();
        Status status = todoItem.getStatus();
        OffsetDateTime createDateTime = todoItem.getCreateDateTime();
        UserDTO userDTO = toUserDTO(todoItem.getUser());
        return new TodoItemDTO(id, content, status, createDateTime, userDTO);
    }

    public TodoItem fromDto(TodoItemDTO todoItemDTO){
        return new TodoItem(todoItemDTO.getContent(), fromUserDto(todoItemDTO.getUserDTO()));
    }

    // USER
    public UserDTO toUserDTO(User user) {
        Integer id = user.getId();
        String name = user.getName();
        return new UserDTO(id, name);
    }

    public User fromUserDto(UserDTO userDTO){
        return new User(userDTO.getName());
    }
}
