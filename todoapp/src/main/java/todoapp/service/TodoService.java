package todoapp.service;

import lib.email.EmailMessageDTO;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Service;
import todoapp.Mapper;
import todoapp.Status;
import todoapp.dto.TodoItemDTO;
import todoapp.entity.TodoItem;
import todoapp.repository.TodoItemRepository;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
    final TodoItemRepository todoItemRepository;
    final UserService userService;
    private final Mapper mapper;

    public TodoService(Mapper mapper, TodoItemRepository todoItemRepository, UserService userService) {
        this.mapper = mapper;
        this.todoItemRepository = todoItemRepository;
        this.userService = userService;
    }

    public Integer addTodoItem(TodoItemDTO todoItemDTO){
        TodoItem todoItem = new TodoItem(todoItemDTO.getContent(), userService.getUserFromSCH());
        return todoItemRepository.save(todoItem).getId();
    }

    public TodoItemDTO getTodoItemById(Integer id){
        Optional<TodoItem> todoItem = todoItemRepository.findByUserAndId(userService.getUserFromSCH(), id);
        if (todoItem.isEmpty()){
            throw new NoSuchElementException("Item with id = " + id + " not found in DB");
        } else
            return todoItem.map(mapper::toDto).orElse(null);
    }

    public void delTodoItemById(Integer id){
        if (todoItemRepository.findByUserAndId(userService.getUserFromSCH(), id).isPresent()) {
            todoItemRepository.deleteById(id);
        } else
            throw new NoSuchElementException("Item with id = " + id + " not found in DB");
    }

    public void finishTodoItemById(Integer id){
        Optional<TodoItem> todoItem = todoItemRepository.findByUserAndId(userService.getUserFromSCH(), id);
        if (todoItem.isPresent() && !todoItem.get().getStatus().equals(Status.DONE)) {
            TodoItem i = todoItem.get();
            i.setFinishDateTime(java.time.OffsetDateTime.now());
            i.setStatus(Status.DONE);
            todoItemRepository.save(i);
        } else
            throw new NoSuchElementException("Item with id = " + id + " not found in DB");
    }

    public void updateTodoItem(Integer id, TodoItemDTO todoItemDTO){
        Optional<TodoItem> todoItemFromDB = todoItemRepository.findByUserAndId(userService.getUserFromSCH(), id);
        if (todoItemFromDB.isPresent()){
            todoItemFromDB.get().setContent(todoItemDTO.getContent());
            todoItemRepository.save(todoItemFromDB.get());
        } else
            throw new NoSuchElementException("Item with id = " + id + " not found in DB");
    }

    public List<TodoItemDTO> findTodoItemByStatus(Status status){
        return todoItemRepository.findByUserAndStatusOrderByCreateDateTime(
                userService.getUserFromSCH(), status)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public EmailMessageDTO genReport(){
        StringBuilder messageBody = new StringBuilder();

        // Сколько задач выполнено
        long finishTaskCount = findTodoItemByStatus(Status.DONE).size();
        messageBody.append("Выполнено задач: ").append(finishTaskCount);

        // сколько осталось
        long activeTaskCount = findTodoItemByStatus(Status.NEW).size();
        messageBody.append("\nАктивных задач: ").append(activeTaskCount);

        // за какое время сделана
        for(TodoItemDTO item: findTodoItemByStatus(Status.DONE)){

            Duration duration = Duration.between(item.getCreateDateTime().toInstant(),
                    item.getFinishDateTime().toInstant());

            messageBody.append("\nЗадача №")
                    .append(item.getId())
                    .append(" выполнялась ")
                    .append(DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss",
                            true));
        }

        return new EmailMessageDTO(userService.getUserFromSCH().getEmail(),
                "Отчёт о задачах от " + java.time.LocalDate.now(),
                messageBody.toString());
    }
}