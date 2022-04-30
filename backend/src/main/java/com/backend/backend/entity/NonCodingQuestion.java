package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NonCodingQuestion extends Question {

    public NonCodingQuestion() {}

    public NonCodingQuestion(int question_id, int user_id, LocalDateTime create_date, int question_difficulty, String question_title, String question_content) {
        super(question_id, user_id, create_date, question_difficulty, question_title, question_content);
    }
}
