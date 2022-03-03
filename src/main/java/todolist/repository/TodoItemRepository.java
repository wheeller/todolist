package todolist.repository;

import org.springframework.data.repository.CrudRepository;
import todolist.Status;
import todolist.entity.TodoItem;
import java.util.List;
import java.util.Optional;

public interface TodoItemRepository extends CrudRepository<TodoItem, Integer> {
    Optional<TodoItem> findById(Integer id);
    List<TodoItem> findByStatusOrderByCreateDateTime(Status status);
}
