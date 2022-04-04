package todoapp.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String password;
    private String email;

    public UserDTO(){}

    public UserDTO(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public UserDTO(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public UserDTO(Integer id, String name, String password, String email){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
