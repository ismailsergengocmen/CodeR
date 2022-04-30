package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NonCodingAttempt extends Attempt {
    private String answer;

    public NonCodingAttempt() {}

    public NonCodingAttempt(int attempt_id, LocalDateTime attempt_start, LocalDateTime attempt_end, int point, String answer) {
        super(attempt_id, attempt_start, attempt_end, point);
        this.answer = answer;
    }
}
