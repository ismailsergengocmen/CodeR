package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class NonCodingQuestion extends Question {
    public NonCodingQuestion(int question_id, int question_difficulty, String question_content, ArrayList<String> question_category) {
        super(question_id, question_difficulty, question_content, question_category);
    }
}
