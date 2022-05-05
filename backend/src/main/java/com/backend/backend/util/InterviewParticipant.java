package com.backend.backend.util;

public class InterviewParticipant {
    private Integer interview_id;
    private Integer user_id;

    public InterviewParticipant() {}

    public InterviewParticipant(Integer interview_id, Integer user_id) {
        this.interview_id = interview_id;
        this.user_id = user_id;
    }

    public Integer getInterview_id() {
        return interview_id;
    }

    public void setInterview_id(int interview_id) {
        this.interview_id = interview_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}