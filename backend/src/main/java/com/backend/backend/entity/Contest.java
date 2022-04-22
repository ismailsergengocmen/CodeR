package com.backend.backend.entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contest {

    private int contest_id;
    private String contest_name;
    private String description;
    private String category;



  public Contest(int contest_id, String contest_name, String description, String category) {
      this.contest_id = contest_id;
      this.contest_name = contest_name;
      this.description = description;
      this.category = category;





  }







}
