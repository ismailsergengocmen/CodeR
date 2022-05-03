package com.backend.backend.entity;

public class BasicQuestion {
    private int question_id;
    private int user_id; // editor_id
    private int question_difficulty;
    private String question_title;

    public BasicQuestion() {}

    public BasicQuestion(int question_id, int user_id, int question_difficulty, String question_title) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.question_difficulty = question_difficulty;
        this.question_title = question_title;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQuestion_difficulty() {
        return question_difficulty;
    }

    public void setQuestion_difficulty(int question_difficulty) {
        this.question_difficulty = question_difficulty;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }
}
