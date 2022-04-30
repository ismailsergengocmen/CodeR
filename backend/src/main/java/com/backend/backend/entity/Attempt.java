package com.backend.backend.entity;

import java.time.LocalDateTime;

public class Attempt {
    private int attempt_id;
    private LocalDateTime attempt_start;
    private LocalDateTime attempt_end;
    private int point;

    public Attempt() {}

    public Attempt(int attempt_id, LocalDateTime attempt_start, LocalDateTime attempt_end, int point) {
        this.attempt_id = attempt_id;
        this.attempt_start = attempt_start;
        this.attempt_end = attempt_end;
        this.point = point;
    }

    public int getAttempt_id() {
        return attempt_id;
    }

    public void setAttempt_id(int attempt_id) {
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
