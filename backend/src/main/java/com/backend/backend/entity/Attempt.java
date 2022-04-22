package com.backend.backend.entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Attempt {
    private int attempt_id;
    private Date attempt_start;
    private int attempt_time;
    private int point;

    public Attempt(int attempt_id, Date attempt_start, int attempt_time, int point) {
        this.attempt_id = attempt_id;
        this.attempt_start = attempt_start;
        this.attempt_time = attempt_time;
        this.point = point;
    }
}
