package todolist;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TodoItemDTO {
    private int id;
    private String content;
    private Status status;
    private OffsetDateTime createDateTime;

    public TodoItemDTO() {
    }

    // can't deserialize ResponseBody without it
    public TodoItemDTO(String content) {
        this.content = content;
    }

    TodoItemDTO(int id, String content, Status status, OffsetDateTime createDateTime){
        this.id = id;
        this.content = content;
        this.status = status;
        this.createDateTime = createDateTime;
    }
}
