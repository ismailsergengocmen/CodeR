package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Interview {
    private int interview_id;
    private String title;

    public Interview() {}

    public Interview(int interview_id, String title) {
        this.interview_id = interview_id;
        this.title = title;
    }
}
