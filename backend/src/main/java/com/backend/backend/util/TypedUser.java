package com.backend.backend.util;

import com.backend.backend.entity.User;

import java.time.LocalDate;

public class TypedUser extends User {
    private String type;

    public TypedUser() {}

    public TypedUser(int user_id, String email, String password, String name, String phone_no, String description, LocalDate last_password_change, String type) {
        super(user_id, email, password, name, phone_no, description, last_password_change);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return new User(
                getUser_id(), getEmail(), getPassword(), getName(), getPhone_no(), getDescription(), getLast_password_change()
        );
    }
}
