package com.backend.backend.controller;

import com.backend.backend.entity.Forum;
import com.backend.backend.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/Forum")
public class ForumController {

    @Autowired
    ForumRepository forumRepository;

    @GetMapping("all")
    public List<Forum> getAllForum() {
        return forumRepository.getAllForums();
    }

    @GetMapping("{forum_id}")
    public Forum getForumWithId(@PathVariable Integer forum_id) {
        return forumRepository.getForumWithId(forum_id);
    }

    @PostMapping("create")
    public Integer createForum(@RequestBody Forum forum) {
        forum.setCreate_date(LocalDateTime.now().withNano(0));
        return forumRepository.createForum(forum);
    }

    @PutMapping("update")
    public Boolean updateForum(@RequestBody Forum forum) {
        return forumRepository.updateForum(forum);
    }

}