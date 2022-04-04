package todoapp.dto;
import lombok.Data;
import todoapp.Status;

import java.time.OffsetDateTime;

@Data
public class TodoItemDTO {
    private int id;
    private String content;
    private Status status;
    private OffsetDateTime createDateTime;
    private OffsetDateTime finishDateTime;
    private UserDTO userDTO;

    public TodoItemDTO() {
    }

    // can't deserialize ResponseBody without it
    public TodoItemDTO(String content, UserDTO userDTO) {
        this.content = content;
        this.userDTO = userDTO;
    }

    public TodoItemDTO(int id, String content, Status status,
                       OffsetDateTime createDateTime, OffsetDateTime finishDateTime, UserDTO userDTO){
        this.id = id;
        this.content = content;
        this.status = status;
        this.createDateTime = createDateTime;
        this.finishDateTime = finishDateTime;
        this.userDTO = userDTO;
    }
}
