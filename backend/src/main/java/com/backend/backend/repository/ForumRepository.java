package com.backend.backend.repository;


import com.backend.backend.entity.BasicQuestion;
import com.backend.backend.entity.Forum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ForumRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    RowMapper<Forum> forumMapper = (rs, rowNum) -> {
        Forum forum = new Forum();
        forum.setForum_id(rs.getInt("forum_id"));
        forum.setQuestion_id(rs.getInt("question_id"));
        forum.setCreate_date(rs.getTimestamp("create_date").toLocalDateTime());
        forum.setTitle(rs.getString("title"));
        return forum;
    };
    RowMapper<BasicQuestion> basicQuestionMapper = (rs, rowNum) -> {
        BasicQuestion question = new BasicQuestion();
        question.setQuestion_id(rs.getInt("question_id"));
        question.setUser_id(rs.getInt("user_id"));
        question.setQuestion_difficulty(rs.getInt("question_difficulty"));
        question.setQuestion_title(rs.getString("question_title"));

        return question;
    };

    RowMapper<Integer> intMapper = (rs, rowNum) -> rs.getInt(1);

    public List<Forum> getAllForums() {
        String sql = "SELECT forum_id, question_id, create_date, title, FROM forum";
        List<Forum> forumList = jdbcTemplate.query(sql, forumMapper);
        return forumList;
    }

    public Forum getForumWithId(Integer forum_id) {
        String sql = "SELECT forum_id, question_id, create_date, title, FROM forum WHERE forum_id = ?";
        try {
            Forum forum = jdbcTemplate.queryForObject(sql, forumMapper, forum_id);
            // Add the forum questions
            sql = "SELECT question_id, user_id, question_difficulty, question_title FROM forum NATURAL JOIN question WHERE forum_id = ?";
            BasicQuestion question = jdbcTemplate.queryForObject(sql, basicQuestionMapper, forum_id);
            forum.setForum_question(question);

            sql = "SELECT question_id FROM forum NATURAL JOIN question WHERE forum_id = ?";
            Integer question_id = jdbcTemplate.queryForObject(sql, intMapper, forum_id);//try
            forum.setQuestion_id(question_id);
            return forum;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer createForum(Forum forum) {
        try {
            // Insert into forum table
            String sql = "INSERT INTO forum (forum_id, question_id, create_date, title) VALUES ( ?, ?, ?, ?)";
            jdbcTemplate.update(sql, forum.getForum_id(), forum.getQuestion_id(), forum.getCreate_date(), forum.getTitle());
            Integer forum_id = jdbcTemplate.queryForObject("SELECT forum_id FROM forum WHERE forum_id = ? AND question_id = ? AND create_date = ? ", int.class, forum.getForum_id(), forum.getQuestion_id(), forum.getCreate_date());
            return forum_id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean updateForum(Forum forum) {
        try {
            String sql = "SELECT forum_id, question_id, create_date, title, FROM forum WHERE forum_id = ?";
            Forum currentForum = jdbcTemplate.queryForObject(sql, forumMapper, forum.getForum_id());

            Forum updatedForum = new Forum();
            updatedForum.setForum_id(forum.getForum_id());
            updatedForum.setQuestion_id(currentForum.getQuestion_id());
            updatedForum.setCreate_date(currentForum.getCreate_date());
            updatedForum.setTitle(currentForum.getTitle());

            if (forum.getForum_id() != null) {
                updatedForum.setForum_id(forum.getForum_id());
            }
            if (forum.getQuestion_id() != null) {
                updatedForum.setQuestion_id(forum.getQuestion_id());
            }
            if (forum.getCreate_date() != null) {
                updatedForum.setCreate_date(forum.getCreate_date());
            }
            if (forum.getTitle() != null) {
                updatedForum.setTitle(forum.getTitle());
            }

            // Update the forum table
            sql = "UPDATE forum SET question_id = ?, create_date = ?,  title = ? WHERE forum_id = ?";
            jdbcTemplate.update(sql, updatedForum.getQuestion_id(), updatedForum.getCreate_date(), updatedForum.getTitle());

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}