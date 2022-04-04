package todoapp;

import org.springframework.stereotype.Component;
import todoapp.dto.TodoItemDTO;
import todoapp.dto.UserDTO;
import todoapp.entity.TodoItem;
import todoapp.entity.User;
import java.time.OffsetDateTime;

@Component
public class Mapper {

    // TodoItem
    public TodoItemDTO toDto(TodoItem todoItem){
        int id = todoItem.getId();
        String content = todoItem.getContent();
        Status status = todoItem.getStatus();
        OffsetDateTime createDateTime = todoItem.getCreateDateTime();
        OffsetDateTime finishDataTime = todoItem.getFinishDateTime();
        UserDTO userDTO = toUserDTO(todoItem.getUser());
        return new TodoItemDTO(id, content, status, createDateTime, finishDataTime, userDTO);
    }

/*    public TodoItem fromDto(TodoItemDTO todoItemDTO){
        return new TodoItem(todoItemDTO.getContent(), fromUserDto(todoItemDTO.getUserDTO()));
    }*/

    // USER
    public UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), null, user.getEmail());
    }

    public User fromUserDto(UserDTO userDTO){
        return new User(userDTO.getName(), userDTO.getEmail());
    }
}
