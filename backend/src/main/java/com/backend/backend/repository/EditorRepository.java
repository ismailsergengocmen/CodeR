package com.backend.backend.repository;

import com.backend.backend.entity.Editor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Editor> rowMapper = (rs, rowNum) -> {
        Editor editor = new Editor();
        editor.setUser_id(rs.getInt("user_id"));
        editor.setEmail(rs.getString("email"));
        editor.setPassword(rs.getString("password"));
        editor.setName(rs.getString("name"));
        editor.setPhone_no(rs.getString("phone_no"));
        editor.setDescription(rs.getString("description"));
        editor.setLast_password_change(rs.getTimestamp("last_password_change").toLocalDateTime());
        editor.setFame_point(rs.getInt("fame_point"));

        return editor;
    };

    public List<Editor> getAllEditors() {
        String sql = "SELECT user_id, email, password, name, phone_no, description, last_password_change, fame_point FROM editor NATURAL JOIN user";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void addNewEditor(int user_id) {
        String sql = "INSERT INTO editor (user_id) VALUES (?)";
        jdbcTemplate.update(sql, user_id);
    }

    public Editor findEditorWithId(int user_id) {
        String sql = "SELECT user_id, email, password, name, phone_no, description, last_password_change, fame_point FROM editor NATURAL JOIN user WHERE user_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, user_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Boolean updateFamePoint(Editor editor) {
        String sql = "UPDATE editor SET fame_point = ? WHERE user_id = ?";

        try {
            jdbcTemplate.update(sql, editor.getFame_point(), editor.getUser_id());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
