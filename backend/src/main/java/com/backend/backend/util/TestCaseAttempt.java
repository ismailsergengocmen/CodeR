package com.backend.backend.util;

public class TestCaseAttempt {
    private Integer test_case_id;
    private Integer attempt_id;
    private String result;

    public TestCaseAttempt() {}

    public TestCaseAttempt(Integer test_case_id, Integer attempt_id, String result) {
        this.test_case_id = test_case_id;
        this.attempt_id = attempt_id;
        this.result = result;
    }

    public Integer getTest_case_id() {
        return test_case_id;
    }

    public void setTest_case_id(Integer test_case_id) {
        this.test_case_id = test_case_id;
    }

    public Integer getAttempt_id() {
        return attempt_id;
    }

    public void setAttempt_id(Integer attempt_id) {
        this.attempt_id = attempt_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
