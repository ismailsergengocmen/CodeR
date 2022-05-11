package com.backend.backend.entity;

import java.time.LocalDateTime;

public class CodingAttempt extends Attempt {
    private String programming_language;
    private String code;

    public CodingAttempt() {}

    public CodingAttempt(Integer attempt_id, LocalDateTime attempt_start, LocalDateTime attempt_end, Integer point, Integer user_id, Integer question_id, String programming_language, String code) {
        super(attempt_id, attempt_start, attempt_end, point, user_id, question_id);
        this.programming_language = programming_language;
        this.code = code;
    }

    public String getProgramming_language() {
        return programming_language;
    }

    public void setProgramming_language(String programming_language) {
        this.programming_language = programming_language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
