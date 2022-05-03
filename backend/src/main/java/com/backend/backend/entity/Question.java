package com.backend.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Question extends BasicQuestion {
    private LocalDateTime create_date;
    private String question_content;
    private List<String> question_category;

    public Question() {}

    public Question(int question_id, int user_id, int question_difficulty, String question_title, LocalDateTime create_date, String question_content, List<String> question_category) {
        super(question_id, user_id, question_difficulty, question_title);
        this.create_date = create_date;
        this.question_content = question_content;
        this.question_category = question_category;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public List<String> getQuestion_category() {
        return question_category;
    }

    public void setQuestion_category(List<String> question_category) {
        this.question_category = question_category;
    }
}
