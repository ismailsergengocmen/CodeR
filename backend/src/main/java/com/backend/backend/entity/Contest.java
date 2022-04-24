package com.backend.backend.entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Contest {
    private int contest_id;
    private int user_id; // editor_id
    private String contest_name;
    private String description;
    private LocalDate start_time;
    private int duration; // TO DO This might need a revision
    private LocalDate create_date;

    public Contest() {}

    public Contest(int contest_id, int user_id, String contest_name, String description, LocalDate start_time, int duration, LocalDate create_date) {
        this.contest_id = contest_id;
        this.user_id = user_id;
        this.contest_name = contest_name;
        this.description = description;
        this.start_time = start_time;
        this.duration = duration;
        this.create_date = create_date;
    }
}
