package com.backend.backend.entity;

import java.time.LocalDateTime;

public class NonCodingAttempt extends Attempt {
    private String answer;

    public NonCodingAttempt() {}

    public NonCodingAttempt(Integer attempt_id, LocalDateTime attempt_start, LocalDateTime attempt_end, Integer point, Integer user_id, Integer question_id, String answer) {
        super(attempt_id, attempt_start, attempt_end, point, user_id, question_id);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
