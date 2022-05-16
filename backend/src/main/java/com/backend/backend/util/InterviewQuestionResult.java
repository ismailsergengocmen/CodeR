package com.backend.backend.util;

public class InterviewQuestionResult {
    private String question_id_name;
    private String question_name;
    private String question_type;
    private Long time_taken;
    private Integer score;
    private String status;

    public InterviewQuestionResult() {
    }

    public InterviewQuestionResult(String question_id_name, String question_name, String question_type, Long time_taken, Integer score, String status) {
        this.question_id_name = question_id_name;
        this.question_name = question_name;
        this.question_type = question_type;
        this.time_taken = time_taken;
        this.score = score;
        this.status = status;
    }

    public String getQuestion_id_name() {
        return question_id_name;
    }

    public void setQuestion_id_name(String question_id_name) {
        this.question_id_name = question_id_name;
    }

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public Long getTime_taken() {
        return time_taken;
    }

    public void setTime_taken(Long time_taken) {
        this.time_taken = time_taken;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
