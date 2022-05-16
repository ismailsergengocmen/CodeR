package com.backend.backend.util;

public class InterviewCodingQuestionResult extends InterviewQuestionResult {
    private String programming_language;
    private String code;

    public InterviewCodingQuestionResult() {}

    public InterviewCodingQuestionResult(String question_id_name, String question_name, String question_type, Long time_taken, Integer score, String status, String programming_language, String code) {
        super(question_id_name, question_name, question_type, time_taken, score, status);
        this.programming_language = programming_language;
        this.code = code;
    }

    public String getProgramming_language() {
        return programming_language;
    }

    public void setProgramming_language(String programming_language) {
        this.programming_language = programming_language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
