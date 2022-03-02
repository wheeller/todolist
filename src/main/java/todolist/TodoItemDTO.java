package todolist;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TodoItemDTO {
    private int id;
    private String content;
    private Status status;
    private OffsetDateTime createDateTime;
    private UserDTO userDTO;

    public TodoItemDTO() {
    }

    // can't deserialize ResponseBody without it
    public TodoItemDTO(String content, UserDTO userDTO) {
        this.content = content;
        this.userDTO = userDTO;
    }

    TodoItemDTO(int id, String content, Status status, OffsetDateTime createDateTime, UserDTO userDTO){
        this.id = id;
        this.content = content;
        this.status = status;
        this.createDateTime = createDateTime;
        this.userDTO = userDTO;
    }
}
