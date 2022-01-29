package todolist;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "todoItem", catalog = "hibernatedb")
@Data
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "create_date_time", nullable = false)
    private OffsetDateTime createDateTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Status status;

    @Basic
    @Column(name = "content", nullable = false)
    private String content;

    public TodoItem() {
    }

    public TodoItem(String content) {
        this.createDateTime = java.time.OffsetDateTime.now();
        this.status = Status.NEW;
        this.content = Objects.requireNonNull(content);
    }
}
