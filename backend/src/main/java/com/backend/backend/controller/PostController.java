package com.backend.backend.controller;

import com.backend.backend.entity.Post;
import com.backend.backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/post")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("all")
    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    @GetMapping("{post_id}")
    public Post getPostWithId(@PathVariable Integer post_id) {
        return postRepository.getPostWithId(post_id);
    }

    @PostMapping("create")
    public Integer createPost(@RequestBody Post post) {
        post.setCreate_date(LocalDateTime.now().withNano(0));
        return postRepository.createPost(post);
    }

    @PutMapping("update")
    public Boolean updatePost(@RequestBody Post post) {
        return postRepository.updatePost(post);
    }

}