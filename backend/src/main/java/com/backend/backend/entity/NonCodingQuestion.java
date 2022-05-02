package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NonCodingQuestion extends Question {

    public NonCodingQuestion() {}

    public NonCodingQuestion(int question_id, int user_id, LocalDateTime create_date, int question_difficulty, String question_title, String question_content, List<String> question_category) {
        super(question_id, user_id, create_date, question_difficulty, question_title, question_content, question_category);
    }
}
