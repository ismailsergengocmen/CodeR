package com.backend.backend.entity;

public class BasicQuestion {
    private Integer question_id;
    private Integer user_id; // editor_id
    private Integer question_difficulty;
    private String question_title;

    public BasicQuestion() {}

    public BasicQuestion(Integer question_id, Integer user_id, Integer question_difficulty, String question_title) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.question_difficulty = question_difficulty;
        this.question_title = question_title;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getQuestion_difficulty() {
        return question_difficulty;
    }

    public void setQuestion_difficulty(Integer question_difficulty) {
        this.question_difficulty = question_difficulty;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }
}
