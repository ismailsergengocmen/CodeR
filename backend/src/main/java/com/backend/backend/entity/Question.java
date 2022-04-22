package com.backend.backend.entity;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter



public class Question {

    private int question_id;
    private int question_difficulty;
    private String question_title;
    private String question_content;
    private ArrayList<String> question_category = new ArrayList<String>();
    public Question(int question_id, int question_difficulty, String question_content, ArrayList<String> question_category){
        this.question_id = question_id;
        this.question_difficulty = question_difficulty;
        this.question_content = question_content;






    }
}
