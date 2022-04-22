package com.backend.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private int userid;
    private String email;
    private String password;
    private String name;
    private String phone_no;
    private String description;


    public User(int userid, String email, String password, String name, String phone_no, String description) {

       this.userid = userid;
       this.email = email;
       this.password = password;
       this.name = name;
       this.phone_no = phone_no;
       this.description = description;


    }























}
