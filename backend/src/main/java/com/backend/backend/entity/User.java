package com.backend.backend.entity;

import java.time.LocalDateTime;

public class User {
    private int user_id;
    private String email;
    private String password;
    private String name;
    private String phone_no;
    private String description;
    private LocalDateTime last_password_change;

    public User() {}

    public User(int user_id, String email, String password, String name, String phone_no, String description, LocalDateTime last_password_change) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone_no = phone_no;
        this.description = description;
        this.last_password_change = last_password_change;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLast_password_change() {
        return last_password_change;
    }

    public void setLast_password_change(LocalDateTime last_password_change) {
        this.last_password_change = last_password_change;
    }
}
