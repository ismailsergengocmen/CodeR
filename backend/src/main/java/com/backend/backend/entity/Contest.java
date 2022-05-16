package com.backend.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Contest {
    private Integer contest_id;
    private Integer user_id; // editor_id
    private String contest_name;
    private String description;
    private LocalDateTime start_time;
    private Integer duration; // TO DO This might need a revision
    private LocalDateTime create_date;
    private List<String> category;
    private List<BasicQuestion> contest_questions;
    private List<Integer> question_ids;
    private List<Sponsor> sponsors;

    public Contest() {}

    public Contest(Integer contest_id, Integer user_id, String contest_name, String description, LocalDateTime start_time, Integer duration, LocalDateTime create_date, List<String> category, List<BasicQuestion> contest_questions, List<Integer> question_ids, List<Sponsor> sponsors) {
        this.contest_id = contest_id;
        this.user_id = user_id;
        this.contest_name = contest_name;
        this.description = description;
        this.start_time = start_time;
        this.duration = duration;
        this.create_date = create_date;
        this.category = category;
        this.contest_questions = contest_questions;
        this.question_ids = question_ids;
        this.sponsors = sponsors;
    }

    public Integer getContest_id() {
        return contest_id;
    }

    public void setContest_id(Integer contest_id) {
        this.contest_id = contest_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getContest_name() {
        return contest_name;
    }

    public void setContest_name(String contest_name) {
        this.contest_name = contest_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
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

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<BasicQuestion> getContest_questions() {
        return contest_questions;
    }

    public void setContest_questions(List<BasicQuestion> contest_questions) {
        this.contest_questions = contest_questions;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public List<Integer> getQuestion_ids() {
        return question_ids;
    }

    public void setQuestion_ids(List<Integer> question_ids) {
        this.question_ids = question_ids;
    }
}
