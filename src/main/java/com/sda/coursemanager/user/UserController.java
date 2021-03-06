package com.sda.coursemanager.user;

import com.sda.coursemanager.user.model.Role;
import com.sda.coursemanager.user.model.User;
import com.sda.coursemanager.user.model.dto.UserDetailsDto;
import com.sda.coursemanager.user.model.dto.UserDto;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/{id}")
    public UserDetailsDto getUser(@PathVariable Long id) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));
        return UserMapper.mapUserToUserDetailsDto(user);
    }

    @GetMapping("/users/")
    public List<UserDto> getAllUsers() {
        return UserMapper.mapUsersToUserDtoList(userRepository.findAll());
    }

    @GetMapping(value="/users/", params="type")
    public List<UserDto> getAllTeachers(@RequestParam("type") String type) {
        Role role = Role.valueOf(type);
        return UserMapper.mapUsersToUserDtoList(userRepository.findByType(role));
    }

}
