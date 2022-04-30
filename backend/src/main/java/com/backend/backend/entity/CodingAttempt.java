package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CodingAttempt extends Attempt {
    private String programming_language;

    public CodingAttempt() {}

    public CodingAttempt(int attempt_id, LocalDateTime attempt_start, LocalDateTime attempt_end, int point, String programming_language) {
        super(attempt_id, attempt_start, attempt_end, point);
        this.programming_language = programming_language;
    }
}
