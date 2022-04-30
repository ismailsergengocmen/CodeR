package com.backend.backend.entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Post {
    private int post_id;
    private int forum_id;
    private int user_id;
    private LocalDateTime create_date;
    private String title;
    private String content;

    public Post() {}

    public Post(int post_id, int forum_id, int user_id, LocalDateTime create_date, String title, String content) {
        this.post_id = post_id;
        this.forum_id = forum_id;
        this.user_id = user_id;
        this.create_date = create_date;
        this.title = title;
        this.content = content;
    }
}
