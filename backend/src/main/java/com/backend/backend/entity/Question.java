package com.backend.backend.entity;
import java.time.LocalDate;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
