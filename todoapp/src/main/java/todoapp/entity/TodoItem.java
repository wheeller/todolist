package todoapp.entity;

import lombok.Data;
import todoapp.Status;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "todoitem", schema = "public", catalog = "todoapp")
@Data
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "create_date_time", nullable = false)
    private OffsetDateTime createDateTime;

    @Basic
    @Column(name = "finish_date_time")
    private OffsetDateTime finishDateTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Status status;

    @Basic
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public TodoItem() {
    }

    public TodoItem(String content, User user) {
        this.createDateTime = java.time.OffsetDateTime.now();
        this.status = Status.NEW;
        this.content = Objects.requireNonNull(content);
        this.user = user;
    }
}
