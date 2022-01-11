package todolist;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class TodoApp {
    private static SessionFactory sessionFactory;
/*
    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        TodoApp todoApp = new TodoApp();
//        Integer firstId = todoApp.AddTodoItem("Add remove function");
//        Integer secondId = todoApp.AddTodoItem("Make sorting");
//        System.out.println("First id = " + firstId + "\nSecond id = " + secondId);
//        todoApp.DelTodoItem(1);
//        todoApp.DelTodoItem(3);
//        todoApp.StatusChangeTodoItem(2);
    }
*/
    public TodoApp() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Integer AddTodoItem(String content){
        Session session = sessionFactory.openSession();
        Transaction transaction;
        Integer todoItemId;

        transaction = session.beginTransaction();
        TodoItem todoItem = new TodoItem(content);
        todoItemId = (Integer) session.save(todoItem);
        if(todoItemId != null)
            todoItem.setId(todoItemId);
        transaction.commit();
        session.close();
        return todoItemId;
    }

    public TodoItem GetTodoItem(Integer todoItemId){
        Session session = sessionFactory.openSession();
        Transaction transaction;
        transaction = session.beginTransaction();
        TodoItem todoItem = (TodoItem) session.get(TodoItem.class,todoItemId);
        session.close();
        return todoItem;
    }


    public void DelTodoItem(Integer todoItemId){
        Session session = sessionFactory.openSession();
        Transaction transaction;
        transaction = session.beginTransaction();
        TodoItem todoItem = (TodoItem) session.get(TodoItem.class,todoItemId);
        session.delete(todoItem);
        transaction.commit();
        session.close();
    }
    public void StatusChangeTodoItem(Integer todoItemId){
        Session session = sessionFactory.openSession();
        Transaction transaction;

        transaction = session.beginTransaction();
        TodoItem todoItem = session.get(TodoItem.class,todoItemId);

        if (todoItem.getStatus().equals("New")) {
            todoItem.setStatus("Done");
        } else {
            todoItem.setStatus("New");
        }

        session.save(todoItem);
        transaction.commit();
        session.close();
    }

    public void ChangeTodoItem(Integer todoItemId, String content){
        Session session = sessionFactory.openSession();
        Transaction transaction;

        transaction = session.beginTransaction();
        TodoItem todoItem = session.get(TodoItem.class,todoItemId);

        if (todoItem != null) {
            todoItem.setContent(content);
            session.save(todoItem);
            transaction.commit();
        }
        session.close();
    }

    public List<TodoItem> getTodoList() {
        return getTodoList("");
    }

    public List<TodoItem> getTodoList(String status){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List <TodoItem>todoItemList;
        transaction = session.beginTransaction();

        if (status.equals("New")) {
            todoItemList = session.createQuery("FROM TodoItem WHERE status = 'New' ").list();
        } else {
            todoItemList = session.createQuery("FROM TodoItem").list();
        }

        if (!todoItemList.isEmpty()){
            // Sort by createDateTime
            Collections.sort(todoItemList, new Comparator<TodoItem>() {
                @Override
                public int compare(TodoItem lhs, TodoItem rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal
                    return lhs.getCreateDate().compareTo(rhs.getCreateDate());
                }
            });
        }
        session.close();
        return todoItemList;
    }
}
