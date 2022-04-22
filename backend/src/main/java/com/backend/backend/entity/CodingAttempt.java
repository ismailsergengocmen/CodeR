package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CodingAttempt extends Attempt {
    private String programming_language;

    public CodingAttempt(int attempt_id, Date attempt_start, int attempt_time, int point, String programming_language) {
        super(attempt_id, attempt_start, attempt_time, point);
        this.programming_language = programming_language;
    }
}
