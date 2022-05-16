package com.backend.backend.util;

public class InterviewNonCodingQuestionResult extends InterviewQuestionResult {
    private String answer;

    public InterviewNonCodingQuestionResult() {}

    public InterviewNonCodingQuestionResult(String question_id_name, String question_name, String question_type, Long time_taken, Integer score, String status, String answer) {
        super(question_id_name, question_name, question_type, time_taken, score, status);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
