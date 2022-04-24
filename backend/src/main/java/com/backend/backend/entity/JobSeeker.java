package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JobSeeker extends User {
    private String CV_url;

    public JobSeeker() {}

    public JobSeeker(int userid, String email, String password, String name, String phone_no, String description, String CV_url, LocalDate last_password_change) {
        super(userid, email, password, name, phone_no, description, last_password_change);
        this.CV_url = CV_url;
    }
}
