package com.backend.backend.entity;
import java.time.LocalDate;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class Question {
    private int question_id;
    private int user_id; // editor_id
    private LocalDate create_date;
    private int question_difficulty;
    private String question_title;
    private String question_content;

    public Question() {}

    public Question(int question_id, int user_id, LocalDate create_date, int question_difficulty, String question_title, String question_content) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.create_date = create_date;
        this.question_difficulty = question_difficulty;
        this.question_title = question_title;
        this.question_content = question_content;
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

    public LocalDate getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
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

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }
}
