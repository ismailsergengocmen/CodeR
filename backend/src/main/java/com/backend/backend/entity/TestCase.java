package com.backend.backend.entity;

public class TestCase {
    private Integer question_id;
    private Integer test_case_id;
    private String input;
    private String output;
    private String test_case_type;

    public TestCase() {}

    public TestCase(Integer question_id, Integer test_case_id, String input, String output, String test_case_type) {
        this.question_id = question_id;
        this.test_case_id = test_case_id;
        this.input = input;
        this.output = output;
        this.test_case_type = test_case_type;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Integer getTest_case_id() {
        return test_case_id;
    }

    public void setTest_case_id(Integer test_case_id) {
        this.test_case_id = test_case_id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getTest_case_type() {
        return test_case_type;
    }

    public void setTest_case_type(String test_case_type) {
        this.test_case_type = test_case_type;
    }
}
