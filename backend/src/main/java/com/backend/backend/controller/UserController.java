package com.backend.backend.controller;

import com.backend.backend.entity.User;
import com.backend.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("all")
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @PostMapping("all")
    public void addNewUser(@RequestBody User user) {
        userRepository.addNewUser(user);
    }

    @GetMapping("{user_id}")
    public User findUserWithId(@PathVariable int user_id) {
        return userRepository.findUserWithId(user_id);
    }
}
