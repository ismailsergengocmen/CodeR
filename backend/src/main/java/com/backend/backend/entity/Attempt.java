package com.backend.backend.entity;

import java.time.LocalDate;

public class Attempt {
    private int attempt_id;
    private LocalDate attempt_start;
    private LocalDate attempt_end;
    private int point;

    public Attempt() {}

    public Attempt(int attempt_id, LocalDate attempt_start, LocalDate attempt_end, int point) {
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

    public LocalDate getAttempt_start() {
        return attempt_start;
    }

    public void setAttempt_start(LocalDate attempt_start) {
        this.attempt_start = attempt_start;
    }

    public LocalDate getAttempt_end() {
        return attempt_end;
    }

    public void setAttempt_end(LocalDate attempt_end) {
        this.attempt_end = attempt_end;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
