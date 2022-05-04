package com.backend.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Challenge extends Question {
    List<String> hints;
    List<TestCase> test_cases;

    public Challenge() {}

    public Challenge(Integer question_id, Integer user_id, Integer question_difficulty, String question_title, LocalDateTime create_date, String question_content, List<String> question_category, List<String> hints, List<TestCase> test_cases) {
        super(question_id, user_id, question_difficulty, question_title, create_date, question_content, question_category);
        this.hints = hints;
        this.test_cases = test_cases;
    }

    public List<String> getHints() {
        return hints;
    }

    public void setHints(List<String> hints) {
        this.hints = hints;
    }

    public List<TestCase> getTest_cases() {
        return test_cases;
    }

    public void setTest_cases(List<TestCase> test_cases) {
        this.test_cases = test_cases;
    }
}
