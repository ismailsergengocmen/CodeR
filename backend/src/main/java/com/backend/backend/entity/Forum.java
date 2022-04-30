package com.backend.backend.entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Forum {
    private int forum_id;
    private int question_id;
    private LocalDateTime create_date;
    private String title;

    public Forum() {}

    public Forum(int forum_id, int question_id, LocalDateTime create_date, String title) {
        this.forum_id = forum_id;
        this.question_id = question_id;
        this.create_date = create_date;
        this.title = title;
    }
}
