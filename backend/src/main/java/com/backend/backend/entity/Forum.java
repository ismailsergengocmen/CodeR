package com.backend.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Forum {
    private Integer forum_id;
    private Integer question_id; //Question
    private LocalDateTime create_date;
    private String title;
    private List<Post> posts;

    public Forum() {}

    public Forum(Integer forum_id, Integer question_id, LocalDateTime create_date, String title, List<Post> posts) {
        this.forum_id = forum_id;
        this.question_id = question_id;
        this.create_date = create_date;
        this.title = title;
        this.posts = posts;
    }

    public Integer getForum_id() {
        return forum_id;
    }

    public void setForum_id(Integer forum_id) {
        this.forum_id = forum_id;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
