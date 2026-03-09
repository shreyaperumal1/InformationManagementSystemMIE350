package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.UserNotFoundException;
import com.group9.postal.model.User;
import com.group9.postal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> retrieveAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    User retrieveUser(@PathVariable("id") Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @GetMapping("/users/role/{role}")
    List<User> retrieveUsersByRole(@PathVariable("role") User.Role role) {
        return repository.findByRole(role);
    }

    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable("id") Long userId) {
        return repository.findById(userId)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setPhone(newUser.getPhone());
                    user.setRole(newUser.getRole());
                    user.setPasswordHash(newUser.getPasswordHash());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setUserId(userId);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long userId) {
        repository.deleteById(userId);
    }
}

