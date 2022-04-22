package com.backend.backend.entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobSeeker extends User {


private String CV_URL;
private String skill;

public JobSeeker(int userid, String email, String password, String name, String phone_no, String description, String CV_URL, String skill){

super(userid, email, password, name, phone_no, description);
this.CV_URL = CV_URL;
this.skill = skill;


}




}
