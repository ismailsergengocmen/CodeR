package com.backend.backend.repository;

import com.backend.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setUser_id(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setPhone_no(rs.getString("phone_no"));
        user.setDescription(rs.getString("description"));
        return user;
    };

    public List<User> getAllUsers() {
        String sql = "SELECT user_id, email, password, name, phone_no, description FROM user";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void addNewUser(User user) {
        String sql = "INSERT INTO user (email, password, name, phone_no, description) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getName(), user.getPhone_no(), user.getDescription());
    }

    public User findUserWithId(int user_id) {
        String sql = "SELECT user_id, email, password, name, phone_no, description FROM user WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, user_id);
    }
}
