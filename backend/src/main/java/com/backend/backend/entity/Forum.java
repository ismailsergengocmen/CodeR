package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Forum {
    private  int question_id;
    private int forum_id;
    private Date create_date;
    private String title;

    public Forum(int question_id, int forum_id, Date create_date, String title) {
        this.question_id = question_id;
        this.forum_id = forum_id;
        this.create_date = create_date;
        this.title = title;
    }
}
