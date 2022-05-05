package com.backend.backend.repository;

import com.backend.backend.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
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

    public List<Post> getAllPosts() {
        String sql = "SELECT post_id, forum_id, user_id, create_date, title, content FROM post";
        return jdbcTemplate.query(sql, postMapper);
    }
    public Post getPostWithId(Integer post_id) {
        String sql = "SELECT post_id, forum_id, user_id, create_date, title, content FROM post WHERE post_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, postMapper, post_id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Integer createPost(Post post) {
        try {
            // Insert into post table
            String sql = "INSERT INTO post (forum_id, user_id, create_date, title, content) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, post.getForum_id(), post.getUser_id(), post.getCreate_date(), post.getTitle(), post.getContent());
            return jdbcTemplate.queryForObject("SELECT post_id FROM post WHERE forum_id = ? AND user_id = ? AND create_date = ?  " , int.class, post.getForum_id(), post.getUser_id(), post.getCreate_date());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean updatePost(Post post) {
        try {
            String sql = "SELECT post_id, forum_id, user_id, create_date, title, content FROM post WHERE post_id = ?";
            Post currentPost = jdbcTemplate.queryForObject(sql, postMapper, post.getPost_id());
            
            Post updatedPost = new Post();
            updatedPost.setPost_id(post.getPost_id());
            updatedPost.setTitle(currentPost.getTitle());
            updatedPost.setContent(currentPost.getContent());

            if(post.getTitle() != null) {
                updatedPost.setTitle(post.getTitle());
            }
            if (post.getContent() != null) {
                updatedPost.setContent(post.getContent());
            }

            // Update the post table
            sql = "UPDATE post SET title = ?, content = ? WHERE post_id = ?";
            jdbcTemplate.update(sql, updatedPost.getTitle(), updatedPost.getContent(), updatedPost.getPost_id());
           
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}