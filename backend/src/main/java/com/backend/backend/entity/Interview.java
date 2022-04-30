package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Interview {
    private int interview_id;
    private String title;
    private LocalDate start_date;
    private int duration;
    private int user_id; // company_id

    public Interview() {}

    public Interview(int interview_id, String title, LocalDate start_date, int duration, int user_id) {
        this.interview_id = interview_id;
        this.title = title;
        this.start_date = start_date;
        this.duration = duration;
        this.user_id = user_id;
    }
}
