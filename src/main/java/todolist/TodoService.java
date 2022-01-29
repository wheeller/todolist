package todolist;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class TodoService {
    private static SessionFactory sessionFactory;

    public TodoService() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Integer add(String content) {
        Session session = sessionFactory.openSession();
        Transaction transaction;
        Integer todoItemId;

        transaction = session.beginTransaction();
        TodoItem todoItem = new TodoItem(content);
        todoItemId = (Integer) session.save(todoItem);
        if (todoItemId != null)
            todoItem.setId(todoItemId);
        transaction.commit();
        session.close();
        return todoItemId;
    }

    public TodoItem get(Integer todoItemId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        TodoItem todoItem = session.get(TodoItem.class, todoItemId);
        session.close();
        return todoItem;
    }


    public void del(Integer todoItemId) {
        Session session = sessionFactory.openSession();
        Transaction transaction;
        transaction = session.beginTransaction();
        TodoItem todoItem = session.get(TodoItem.class, todoItemId);
        session.delete(todoItem);
        transaction.commit();
        session.close();
    }

    public void finish(Integer todoItemId) {
        Session session = sessionFactory.openSession();
        Transaction transaction;

        transaction = session.beginTransaction();
        TodoItem todoItem = session.get(TodoItem.class, todoItemId);

        todoItem.setStatus(Status.DONE);

        session.save(todoItem);
        transaction.commit();
        session.close();
    }

    public void modify(Integer todoItemId, String content) {
        Session session = sessionFactory.openSession();
        Transaction transaction;

        transaction = session.beginTransaction();
        TodoItem todoItem = session.get(TodoItem.class, todoItemId);

        if (todoItem != null) {
            todoItem.setContent(content);
            session.save(todoItem);
            transaction.commit();
        }
        session.close();
    }

    public List<TodoItem> getTodoList(Status status) {
        Session session = sessionFactory.openSession();
        List<TodoItem> todoItemList;
        session.beginTransaction();

        if (status == null)
            todoItemList = session.
                    createQuery("FROM TodoItem order by createDateTime", TodoItem.class).list();
        else
            todoItemList = session.
                    createQuery("FROM TodoItem WHERE status = '" + status + "' order by createDateTime",
                            TodoItem.class).list();

        session.close();
        return todoItemList;
    }
}
