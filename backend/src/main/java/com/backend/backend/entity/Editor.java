package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Editor extends User {
    private int fame_point;

    public Editor() {}

    public Editor(int userid, String email, String password, String name, String phone_no, String description, int fame_point, LocalDate last_password_change) {
        super(userid, email, password, name, phone_no, description, last_password_change);
        this.fame_point = fame_point;
    }
}
