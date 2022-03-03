package todolist.service;

import todolist.Mapper;
import todolist.dto.UserDTO;
import todolist.entity.User;
import todolist.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.context.annotation.Configuration
public class UserService {
    final UserRepository userRepository;
    final Mapper mapper;

    UserService(Mapper mapper, UserRepository userRepository){
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public Integer addUser(UserDTO userDTO){
        return userRepository.save(mapper.fromUserDto(userDTO)).getId();
    }

    public List<UserDTO> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(mapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new NoSuchElementException("User with id = " + id + " not found in DB");
        } else
            return mapper.toUserDTO(user.get());
    }

    // delete user with all his todoItems
    public void delUserById(Integer id){
        if (userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        } else
            throw new NoSuchElementException("User with id = " + id + " not found in DB");
    }
}
