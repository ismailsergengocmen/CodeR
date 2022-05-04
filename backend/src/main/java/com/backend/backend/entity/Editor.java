package com.backend.backend.entity;

import java.time.LocalDateTime;

public class Editor extends User {
    private Integer fame_point;

    public Editor() {}

    public Editor(Integer userid, String email, String password, String name, String phone_no, String description, int fame_point, LocalDateTime last_password_change) {
        super(userid, email, password, name, phone_no, description, last_password_change);
        this.fame_point = fame_point;
    }

    public Integer getFame_point() {
        return fame_point;
    }

    public void setFame_point(Integer fame_point) {
        this.fame_point = fame_point;
    }
}
