package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NonCodingAttempt extends Attempt {
    private String answer;

    public NonCodingAttempt() {}

    public NonCodingAttempt(int attempt_id, Date attempt_start, int attempt_time, int point, String answer) {
        super(attempt_id, attempt_start, attempt_time, point);
        this.answer = answer;
    }
}
