package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JobSeeker extends User {
    private String cv_url;

    public JobSeeker() {}

    public JobSeeker(int userid, String email, String password, String name, String phone_no, String description, String cv_url, LocalDate last_password_change) {
        super(userid, email, password, name, phone_no, description, last_password_change);
        this.cv_url = cv_url;
    }
}
