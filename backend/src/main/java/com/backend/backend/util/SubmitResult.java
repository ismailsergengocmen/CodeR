package com.backend.backend.util;

import java.util.List;

public class SubmitResult {
    private List<TestCaseAttempt> testCaseAttempts;
    private Integer point;

    public SubmitResult() {}

    public SubmitResult(List<TestCaseAttempt> testCaseAttempts, Integer point) {
        this.testCaseAttempts = testCaseAttempts;
        this.point = point;
    }

    public List<TestCaseAttempt> getTestCaseAttempts() {
        return testCaseAttempts;
    }

    public void setTestCaseAttempts(List<TestCaseAttempt> testCaseAttempts) {
        this.testCaseAttempts = testCaseAttempts;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
