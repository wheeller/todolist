package todolist;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TodoItemRepository extends CrudRepository<TodoItem, Integer> {
    Optional<TodoItem> findById(Integer id);
    List<TodoItem> findByStatusOrderByCreateDateTime(Status status);
}
