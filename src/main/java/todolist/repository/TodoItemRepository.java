package todolist.repository;

import org.springframework.data.repository.CrudRepository;
import todolist.Status;
import todolist.entity.TodoItem;
import todolist.entity.User;

import java.util.List;
import java.util.Optional;

public interface TodoItemRepository extends CrudRepository<TodoItem, Integer> {
    Optional<TodoItem> findByUserAndId(User user, Integer id);
    List<TodoItem> findByUserAndStatusOrderByCreateDateTime(User user, Status status);
}
