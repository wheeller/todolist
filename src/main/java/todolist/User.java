package todolist;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "my", catalog = "hibernatedb")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TodoItem> todoItemList;

    public User(){}

    public User(String name){
        this.name = name;
    }
}
