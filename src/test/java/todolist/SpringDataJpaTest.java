/*
package todolist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SpringDataJpaTest {
    final TodoService todoService;
    final TodoItemRepository todoItemRepository;
    private final Mapper mapper;

    private SpringDataJpaTest(TodoService todoService, Mapper mapper, TodoItemRepository todoItemRepository) {
        this.todoService = todoService;
        this.mapper = mapper;
        this.todoItemRepository = todoItemRepository;
    }

    @Test
    public void should_find_no_items_if_repository_is_empty() {
        Iterable<TodoItem> tutorials = todoItemRepository.findAll();
        assertThat(tutorials).isEmpty();
    }
}
*/
