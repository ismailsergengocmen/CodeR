package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Post {
    private int forum_id;
    private int post_id;
    private Date create_date;
    private String title;
    private String content;

    public Post(int forum_id, int post_id, Date create_date, String title, String content) {
        this.forum_id = forum_id;
        this.post_id = post_id;
        this.create_date = create_date;
        this.title = title;
        this.content = content;
    }
}
