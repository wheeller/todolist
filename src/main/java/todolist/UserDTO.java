package todolist;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String name;

    UserDTO(String name){
        this.name = name;
    }

    UserDTO(Integer id, String name){
        this.id = id;
        this.name = name;
    }
}
