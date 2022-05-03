package com.backend.backend.util;

public class ContestParticipant {
    private int contest_id;
    private int user_id;

    public ContestParticipant() {}

    public ContestParticipant(int contest_id, int user_id) {
        this.contest_id = contest_id;
        this.user_id = user_id;
    }

    public int getContest_id() {
        return contest_id;
    }

    public void setContest_id(int contest_id) {
        this.contest_id = contest_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
