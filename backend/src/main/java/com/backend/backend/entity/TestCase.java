package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCase {
    private int question_id;
    private int test_case_id;
    private String input;
    private String output;
    private String test_case_type;

    public TestCase() {}

    public TestCase(int question_id, int test_case_id, String input, String output, String test_case_type) {
        this.question_id = question_id;
        this.test_case_id = test_case_id;
        this.input = input;
        this.output = output;
        this.test_case_type = test_case_type;
    }
}
