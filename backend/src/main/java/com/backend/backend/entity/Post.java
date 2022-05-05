package com.backend.backend.entity;


import java.time.LocalDateTime;


public class Post {
    private Integer post_id;
    private Integer forum_id;
    private Integer user_id;
    private LocalDateTime create_date;
    private String title;
    private String content;

    public Post() {}

    public Post(Integer post_id, Integer forum_id, Integer user_id, LocalDateTime create_date, String title, String content) {
        this.post_id = post_id;
        this.forum_id = forum_id;
        this.user_id = user_id;
        this.create_date = create_date;
        this.title = title;
        this.content = content;
    }

    public Integer getPost_id(){
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public Integer getForum_id(){
        return forum_id;
    }

    public void setForum_id(Integer forum_id) {
        this.forum_id = forum_id;
    }

    public Integer getUser_id(){
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    
    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }






}
