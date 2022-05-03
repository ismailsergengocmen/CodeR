package com.backend.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

public class NonCodingQuestion extends Question {

    public NonCodingQuestion() {}

    public NonCodingQuestion(int question_id, int user_id, int question_difficulty, String question_title, LocalDateTime create_date, String question_content, List<String> question_category) {
        super(question_id, user_id, question_difficulty, question_title, create_date, question_content, question_category);
    }
}
