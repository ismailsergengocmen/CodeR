package com.backend.backend.entity;

import java.time.LocalDateTime;

public class Attempt {
    private Integer attempt_id;
    private LocalDateTime attempt_start;
    private LocalDateTime attempt_end;
    private Integer point;
    private Integer user_id;
    private Integer question_id;

    public Attempt() {}

    public Attempt(Integer attempt_id, LocalDateTime attempt_start, LocalDateTime attempt_end, Integer point, Integer user_id, Integer question_id) {
        this.attempt_id = attempt_id;
        this.attempt_start = attempt_start;
        this.attempt_end = attempt_end;
        this.point = point;
        this.user_id = user_id;
        this.question_id = question_id;
    }

    public Integer getAttempt_id() {
        return attempt_id;
    }

    public void setAttempt_id(Integer attempt_id) {
        this.attempt_id = attempt_id;
    }

    public LocalDateTime getAttempt_start() {
        return attempt_start;
    }

    public void setAttempt_start(LocalDateTime attempt_start) {
        this.attempt_start = attempt_start;
    }

    public LocalDateTime getAttempt_end() {
        return attempt_end;
    }

    public void setAttempt_end(LocalDateTime attempt_end) {
        this.attempt_end = attempt_end;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }
}
