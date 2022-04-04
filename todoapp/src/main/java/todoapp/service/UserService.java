package todoapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import todoapp.Mapper;
import todoapp.auth.TokenService;
import todoapp.dto.UserDTO;
import todoapp.entity.User;
import todoapp.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    final UserRepository userRepository;
    final Mapper mapper;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TokenService tokenService;


    UserService(Mapper mapper, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService){
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
    }

    public Integer addUser(UserDTO userDTO){
        User user = mapper.fromUserDto(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user).getId();
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

    public UserDTO getUserByName(String name){
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty()){
            throw new NoSuchElementException("User with name = " + name + " not found in DB");
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

    public String authorize(UserDTO userDTO){
        User userFromDB = userRepository.findByName(userDTO.getName()).orElse(null);
        String token;
        if (userFromDB == null) {
            throw new UsernameNotFoundException("User not found");
        }
            if (bCryptPasswordEncoder.matches(userDTO.getPassword(), userFromDB.getPassword())){
                logger.debug("Password matches for user: " + userDTO.getName());
                token = tokenService.generateToken(userDTO);
                return token;
            } else {
                logger.debug("Password NOT matches for user: " + userDTO.getName());
                throw new BadCredentialsException("Wrong password for user :" + userDTO.getName());
            }
    }

    public User getUserFromSCH(){
        Optional<User> user = userRepository.findByName(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isEmpty()){
            throw new NoSuchElementException("User " + SecurityContextHolder.getContext().getAuthentication()
                    .getName() + " not found in DB");
        } else
            return user.orElse(null);
    }
}
