package com.backend.backend.entity;

import java.time.LocalDateTime;

import java.util.List;

public class Interview {
    private Integer interview_id;
    private String title;
    private LocalDateTime start_date;
    private Integer duration;
    private LocalDateTime create_date;
    private Integer user_id; // company_id
    private List<BasicQuestion> interview_questions;
    private List<Integer> interview_question_ids;

    public Interview() {}

    public Interview(Integer interview_id, String title, LocalDateTime start_date, Integer duration, LocalDateTime create_date, Integer user_id, List<BasicQuestion> interview_questions, List<Integer> interview_question_ids) {
        this.interview_id = interview_id;
        this.title = title;
        this.start_date = start_date;
        this.duration = duration;
        this.create_date = create_date;
        this.user_id = user_id;
        this.interview_questions = interview_questions;
        this.interview_question_ids = interview_question_ids;
    }

    public Integer getInterview_id() {
        return interview_id;
    }

    public void setInterview_id(Integer interview_id) {
        this.interview_id = interview_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public List<BasicQuestion> getInterview_questions() {
        return interview_questions;
    }

    public void setInterview_questions(List<BasicQuestion> interview_questions) {
        this.interview_questions = interview_questions;
    }

    public List<Integer> getInterview_question_ids() {
        return interview_question_ids;
    }

    public void setInterview_question_ids(List<Integer> interview_question_ids) {
        this.interview_question_ids = interview_question_ids;
    }
}