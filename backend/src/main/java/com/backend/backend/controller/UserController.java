package com.backend.backend.controller;

import com.backend.backend.entity.User;
import com.backend.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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

    @GetMapping("{user_id}")
    public User findUserWithId(@PathVariable int user_id) {
        return userRepository.findUserWithId(user_id);
    }

    @PostMapping("all")
    public boolean addNewUser(@RequestBody User user) {
        try {
            userRepository.addNewUser(user);
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
}
