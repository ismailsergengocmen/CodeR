package com.backend.backend.repository;

import com.backend.backend.entity.BasicQuestion;
import com.backend.backend.entity.Forum;
import com.backend.backend.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    RowMapper<Post> postMapper = (rs, rowNum) -> {
        Post post = new Post();
        post.setPost_id(rs.getInt("post_id"));
        post.setForum_id(rs.getInt("forum_id"));
        post.setUser_id(rs.getInt("user_id"));
        post.setCreate_date(rs.getTimestamp("create_date").toLocalDateTime());
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));

        return post;
    };

    public List<Forum> getAllForums() {
        String sql = "SELECT forum_id, question_id, create_date, title FROM forum";
        return jdbcTemplate.query(sql, forumMapper);
    }

    public Forum getForumWithId(Integer question_id) {
        String sql = "SELECT forum_id, question_id, create_date, title FROM forum WHERE question_id = ?";
        try {
            Forum forum = jdbcTemplate.queryForObject(sql, forumMapper, question_id);

            sql = "SELECT post_id, forum_id, user_id, create_date, title, content FROM post WHERE forum_id = ?";
            List<Post> posts = jdbcTemplate.query(sql, postMapper, forum.getForum_id());
            forum.setPosts(posts);
            return forum;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer createForum(Forum forum) {
        try {
            // Insert into forum table
            String sql = "INSERT INTO forum (question_id, create_date, title) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, forum.getQuestion_id(), LocalDateTime.now().withNano(0), forum.getTitle());
            return jdbcTemplate.queryForObject("SELECT forum_id FROM forum WHERE question_id = ? AND create_date = ? AND title = ?", int.class, forum.getQuestion_id(), forum.getCreate_date(), forum.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}