package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Challenge extends Question {
    private ArrayList<String> hints;
    private ArrayList<TestCase> test_cases;

    public Challenge(int question_id, int question_difficulty, String question_content, ArrayList<String> question_category, ArrayList<String> hints, ArrayList<TestCase> test_cases) {
        super(question_id, question_difficulty, question_content, question_category);
        this.hints = hints;
        this.test_cases = test_cases;
    }
}
