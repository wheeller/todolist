package todolist.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String name;

    UserDTO(){}

    UserDTO(String name){
        this.name = name;
    }

    public UserDTO(Integer id, String name){
        this.id = id;
        this.name = name;
    }
}