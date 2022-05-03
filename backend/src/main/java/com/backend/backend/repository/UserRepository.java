package com.backend.backend.repository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.backend.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = new User();
        user.setUser_id(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setPhone_no(rs.getString("phone_no"));
        user.setDescription(rs.getString("description"));
        user.setLast_password_change(rs.getTimestamp("last_password_change").toLocalDateTime());

        return user;
    };

    public List<User> getAllUsers() {
        String sql = "SELECT user_id, email, password, name, phone_no, description, last_password_change FROM user";
        return jdbcTemplate.query(sql, userMapper);
    }

    public Integer addNewUser(User user) throws SQLException {
        String sql = "INSERT INTO user (email, password, name, phone_no, description, last_password_change) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getName(), user.getPhone_no(), user.getDescription(), user.getLast_password_change());
            return jdbcTemplate.queryForObject("SELECT user_id FROM user WHERE email = ?", int.class, user.getEmail());
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public User findUserWithId(int user_id) {
        String sql = "SELECT user_id, email, password, name, phone_no, description, last_password_change FROM user WHERE user_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, userMapper, user_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer isUserExists(User user) {
        String sql = "SELECT user_id, password FROM user WHERE email = ?";

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, user.getEmail());
            Integer user_id = (Integer) result.get("user_id");
            String hashed_password = (String) result.get("password");

            if (BCrypt.verifyer().verify(user.getPassword().toCharArray(), hashed_password).verified) {
                return user_id;
            } else {
                return null;
            }
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Boolean updateUser(User user) {
        try {
            String sql = "SELECT user_id, email, password, name, phone_no, description, last_password_change FROM user WHERE user_id = ?";
            User currentUser = jdbcTemplate.queryForObject(sql, userMapper, user.getUser_id());

            sql = "UPDATE user SET email = ?, password = ?, name = ?, phone_no = ?, description = ?, last_password_change = ? WHERE user_id = ?";
            User updatedUser = new User(currentUser.getUser_id(), currentUser.getEmail(), currentUser.getPassword(), currentUser.getName(), currentUser.getPhone_no(), currentUser.getDescription(), currentUser.getLast_password_change());

            if (user.getPassword()!= null && !BCrypt.verifyer().verify(user.getPassword().toCharArray(), currentUser.getPassword()).verified) {
                String hashedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
                updatedUser.setPassword(hashedPassword);
                updatedUser.setLast_password_change(LocalDateTime.now().withNano(0));
            }
            if (user.getName() != null && !user.getName().equals(currentUser.getName())) {
                updatedUser.setName(user.getName());
            }
            if(user.getPhone_no() != null && !user.getPhone_no().equals(currentUser.getPhone_no())) {
                updatedUser.setPhone_no(user.getPhone_no());
            }
            if(user.getDescription() != null && !user.getDescription().equals(currentUser.getDescription())) {
                updatedUser.setDescription(user.getDescription());
            }
            jdbcTemplate.update(sql, updatedUser.getEmail(), updatedUser.getPassword(), updatedUser.getName(), updatedUser.getPhone_no(), updatedUser.getDescription(), updatedUser.getLast_password_change(), updatedUser.getUser_id());

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
