package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.UserNotFoundException;
import com.group9.postal.model.User;
import com.group9.postal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.group9.postal.dto.LoginRequest;
import com.group9.postal.dto.LoginResponse;

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

    //Login Method
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request) {
        Optional<User> userOpt = repository.findByEmail(request.getEmail());

        if (userOpt.isEmpty() || !userOpt.get().getPasswordHash().equals(request.getPassword())) {
            LoginResponse response = new LoginResponse();
            response.setMessage("Invalide email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = userOpt.get();
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(String.valueOf(user.getRole()));
        response.setMessage("Login successful");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long userId) {
        repository.deleteById(userId);
    }
}

