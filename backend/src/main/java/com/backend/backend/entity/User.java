package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class User {
    private int user_id;
    private String email;
    private String password;
    private String name;
    private String phone_no;
    private String description;
    private LocalDate last_password_change;

    public User() {}

    public User(int user_id, String email, String password, String name, String phone_no, String description, LocalDate last_password_change) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone_no = phone_no;
        this.description = description;
        this.last_password_change = last_password_change;
    }
}
