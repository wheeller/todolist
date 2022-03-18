package todolist.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String password;

    public UserDTO(){}

    public UserDTO(String name, String password){
        this.name = name;
        this.password = password;
    }

    public UserDTO(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public UserDTO(Integer id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
