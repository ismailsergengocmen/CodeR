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

    @GetMapping("type/{user_id}")
    public String findUserType(@PathVariable int user_id) {
        return userRepository.findUserType(user_id);
    }

    @PostMapping("all")
    public Boolean addNewUser(@RequestBody User user) {
        try {
            userRepository.addNewUser(user);
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    @PutMapping("update")
    public Boolean updateUser(@RequestBody User user) {
        return userRepository.updateUser(user);
    }
}
