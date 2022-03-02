package todolist;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findById(Integer id);
    Optional<User> findByName(String name);
    List<User> findAll();
}
