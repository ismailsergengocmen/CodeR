package com.backend.backend.util;

import com.backend.backend.entity.NonCodingAttempt;

import java.util.List;

public class InterviewResult {
    private Integer user_id;
    private String user_name;
    private List<InterviewCodingQuestionResult> interviewCodingQuestionResults;
    private List<InterviewNonCodingQuestionResult> interviewNonCodingQuestionResults;

    public InterviewResult() {
    }

    public InterviewResult(List<InterviewCodingQuestionResult> interviewCodingQuestionResults, List<InterviewNonCodingQuestionResult> interviewNonCodingQuestionResults, Integer user_id, String user_name) {
        this.interviewCodingQuestionResults = interviewCodingQuestionResults;
        this.interviewNonCodingQuestionResults = interviewNonCodingQuestionResults;
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public List<InterviewCodingQuestionResult> getInterviewCodingQuestionResults() {
        return interviewCodingQuestionResults;
    }

    public void setInterviewCodingQuestionResults(List<InterviewCodingQuestionResult> interviewCodingQuestionResults) {
        this.interviewCodingQuestionResults = interviewCodingQuestionResults;
    }

    public List<InterviewNonCodingQuestionResult> getInterviewNonCodingQuestionResults() {
        return interviewNonCodingQuestionResults;
    }

    public void setInterviewNonCodingQuestionResults(List<InterviewNonCodingQuestionResult> interviewNonCodingQuestionResults) {
        this.interviewNonCodingQuestionResults = interviewNonCodingQuestionResults;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
