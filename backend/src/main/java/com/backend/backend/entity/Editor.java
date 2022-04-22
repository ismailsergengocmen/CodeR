package com.backend.backend.entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Editor extends User{
    private int fame_point;

    public Editor(int userid, String email, String password, String name, String phone_no, String description, String CV_URL, String skill){

        super(userid, email, password, name, phone_no, description);
        this.fame_point = fame_point;


    }



}
