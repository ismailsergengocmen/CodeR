package com.backend.backend.entity;

import java.time.LocalDate;

public class Company extends User{

    public Company(){}

    public Company(int userid, String email, String password, String name, String phone_no, String description, LocalDate last_password_change){
        super(userid, email, password, name, phone_no, description, last_password_change);
    }

}
