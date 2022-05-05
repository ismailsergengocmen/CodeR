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
        post.setCreate_date(rs.getTimestamp("start_date").toLocalDateTime());
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        return post;
    };

    public List<Post> getAllPosts() {
        String sql = "SELECT post_id, forum_id, user_id, start_date, title, content FROM post";
        List<Post> postList = jdbcTemplate.query(sql, postMapper);
        return postList;
    }
    public Post getPostWithId(Integer post_id) {
        String sql = "SELECT post_id, forum_id, user_id, start_date, title, content FROM post WHERE post_id = ?";
        try {
            Post post = jdbcTemplate.queryForObject(sql, postMapper, post_id);
            return post;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Integer createPost(Post post) {
        try {
            // Insert into post table
            String sql = "INSERT INTO post (post_id, forum_id, user_id, start_date, title, content) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, post.getPost_id(), post.getForum_id(), post.getUser_id(), post.getCreate_date(), post.getTitle(), post.getContent());
            Integer post_id = jdbcTemplate.queryForObject("SELECT post_id FROM post WHERE post_id = ? AND forum_id = ? AND user_id = ? AND start_date = ?  " , int.class, post.getPost_id(),post.getForum_id(),post.getUser_id(), post.getCreate_date());
            return post_id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean updatePost(Post post) {
        try {
            String sql = "SELECT post_id, forum_id, user_id, start_date, title, content FROM post WHERE post_id = ?";
            Post currentPost = jdbcTemplate.queryForObject(sql, postMapper, post.getPost_id());
            
            Post updatedPost = new Post();
            updatedPost.setPost_id(post.getPost_id());
            updatedPost.setForum_id(currentPost.getForum_id());
            updatedPost.setUser_id(currentPost.getUser_id());
            updatedPost.setCreate_date(currentPost.getCreate_date());
            updatedPost.setTitle(currentPost.getTitle());
            updatedPost.setContent(currentPost.getContent());

            if (post.getForum_id() != null) {
                updatedPost.setForum_id(post.getForum_id());
            }

            if (post.getUser_id() != null) {
                updatedPost.setUser_id(post.getUser_id());
            }
       
            if (post.getCreate_date() != null) {
                updatedPost.setCreate_date(post.getCreate_date());
            }
            if(post.getTitle() != null) {
                updatedPost.setTitle(post.getTitle());
            }
            if (post.getContent() != null) {
                updatedPost.setContent(post.getContent());
            }

            // Update the post table
            sql = "UPDATE post SET forum_id = ?, user_id = ?,  start_date = ?, title = ?, content = ? WHERE post_id = ?";
            jdbcTemplate.update(sql, updatedPost.getForum_id(), updatedPost.getUser_id(), updatedPost.getCreate_date(), updatedPost.getTitle(), updatedPost.getContent());
           
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}