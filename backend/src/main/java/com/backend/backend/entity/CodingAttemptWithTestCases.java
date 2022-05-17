package com.backend.backend.entity;

import java.util.List;

public class CodingAttemptWithTestCases {
    private CodingAttempt codingAttempt;
    private List<TestCaseAttempt> testCaseAttempt;

    public CodingAttemptWithTestCases() {
    }

    public CodingAttemptWithTestCases(CodingAttempt codingAttempt, List<TestCaseAttempt> testCaseAttempt) {
        this.codingAttempt = codingAttempt;
        this.testCaseAttempt = testCaseAttempt;
    }

    public CodingAttempt getCodingAttempt() {
        return codingAttempt;
    }

    public void setCodingAttempt(CodingAttempt codingAttempt) {
        this.codingAttempt = codingAttempt;
    }

    public List<TestCaseAttempt> getTestCaseAttempt() {
        return testCaseAttempt;
    }

    public void setTestCaseAttempt(List<TestCaseAttempt> testCaseAttempt) {
        this.testCaseAttempt = testCaseAttempt;
    }
}
