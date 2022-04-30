package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NonCodingAttempt extends Attempt {
    private String answer;

    public NonCodingAttempt() {}

    public NonCodingAttempt(int attempt_id, LocalDate attempt_start, LocalDate attempt_end, int point, String answer) {
        super(attempt_id, attempt_start, attempt_end, point);
        this.answer = answer;
    }
}
