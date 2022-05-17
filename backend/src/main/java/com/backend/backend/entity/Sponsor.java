package com.backend.backend.entity;

public class Sponsor {
    private Integer contest_id;
    private String sponsor_name;
    private Integer user_id;
    private Integer money;

    public Sponsor() {}

    public Sponsor(Integer contest_id, String sponsor_name, Integer user_id, Integer money) {
        this.contest_id = contest_id;
        this.sponsor_name = sponsor_name;
        this.user_id = user_id;
        this.money = money;
    }

    public Integer getContest_id() {
        return contest_id;
    }

    public void setContest_id(Integer contest_id) {
        this.contest_id = contest_id;
    }

    public String getSponsor_name() {
        return sponsor_name;
    }

    public void setSponsor_name(String sponsor_name) {
        this.sponsor_name = sponsor_name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
