package com.backend.backend.repository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.backend.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
        // System.out.println(rs.getTimestamp("last_password_change"));
        // user.setLast_password_change(getLocalDate(rs, "last_password_change"));
        // user.setLast_password_change(LocalDate.parse(rs.getString("last_password_change"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return user;
    };

    public List<User> getAllUsers() {
        String sql = "SELECT user_id, email, password, name, phone_no, description FROM user";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Integer addNewUser(User user) throws SQLException {
        String sql = "INSERT INTO user (email, password, name, phone_no, description) VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getName(), user.getPhone_no(), user.getDescription());
            return jdbcTemplate.queryForObject("SELECT user_id FROM user WHERE email = ?", int.class, user.getEmail());
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public User findUserWithId(int user_id) {
        String sql = "SELECT user_id, email, password, name, phone_no, description FROM user WHERE user_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, user_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer isUserExists(User user) {
        String sql = "SELECT user_id, password FROM user WHERE email = ?";

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, new Object[] {user.getEmail()});
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
}
