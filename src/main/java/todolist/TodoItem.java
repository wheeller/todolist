package todolist;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "todoItem", schema = "", catalog = "hibernatedb")
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;
    @Basic
    @Column(name = "create_date_time", nullable = false, insertable = true, updatable = true)
    private OffsetDateTime createDateTime;
    @Basic
    @Column(name = "status", nullable = false, insertable = true, updatable = true)
    private String status;
    @Basic
    @Column(name = "content", nullable = false, insertable = true, updatable = true)
    private String content;

    public TodoItem() {
    }

    public TodoItem(String content) {
        this.createDateTime = java.time.OffsetDateTime.now();
        this.status = "New";
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OffsetDateTime getCreateDate() {
        return createDateTime;
    }

    public void setCreateDate(OffsetDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return "id: " + id + "\nCreation date: " + createDateTime
                + "\nStatus: " + status + "\nContent: " + content + "\n";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return id == todoItem.id && createDateTime.equals(todoItem.createDateTime) && status.equals(todoItem.status) && content.equals(todoItem.content);
    }
}
