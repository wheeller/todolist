package todolist.service;

import todolist.Mapper;
import todolist.Status;
import todolist.dto.TodoItemDTO;
import todolist.entity.TodoItem;
import todolist.repository.TodoItemRepository;
import todolist.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.context.annotation.Configuration
public class TodoService {
    final TodoItemRepository todoItemRepository;
    final UserRepository userRepository;
    private final Mapper mapper;

    public TodoService(Mapper mapper, TodoItemRepository todoItemRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.todoItemRepository = todoItemRepository;
        this.userRepository = userRepository;
    }

    public Integer addTodoItem(TodoItemDTO todoItemDTO){
        TodoItem todoItem = new TodoItem(todoItemDTO.getContent(),
                userRepository.findByName(todoItemDTO.getUserDTO().getName()).orElse(null));
        return todoItemRepository.save(todoItem).getId();
    }

    public TodoItemDTO getTodoItemById(Integer id){
        Optional<TodoItem> todoItem = todoItemRepository.findById(id);
        if (todoItem.isEmpty()){
            throw new NoSuchElementException("Item with id = " + id + " not found in DB");
        } else
            return todoItem.map(mapper::toDto).orElse(null);
    }

    public void delTodoItemById(Integer id){
        if (todoItemRepository.findById(id).isPresent()) {
            todoItemRepository.deleteById(id);
        } else
            throw new NoSuchElementException("Item with id = " + id + " not found in DB");
    }

    public void finishTodoItemById(Integer id){
        Optional<TodoItem> todoItem = todoItemRepository.findById(id);
        if (todoItem.isPresent()) {
            TodoItem i = todoItem.get();
            i.setStatus(Status.DONE);
            todoItemRepository.save(i);
        } else
            throw new NoSuchElementException("Item with id = " + id + " not found in DB");
    }

    public void updateTodoItem(Integer id, TodoItemDTO todoItemDTO){
        Optional<TodoItem> todoItemFromDB = todoItemRepository.findById(id);
        if (todoItemFromDB.isPresent()){
            todoItemFromDB.get().setContent(todoItemDTO.getContent());
            todoItemRepository.save(todoItemFromDB.get());
        } else
            throw new NoSuchElementException("Item with id = " + id + " not found in DB");
    }

    public List<TodoItemDTO> findTodoItemByStatus(Status status){
        return todoItemRepository.findByStatusOrderByCreateDateTime(status)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}