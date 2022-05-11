package com.backend.backend.repository;

import com.backend.backend.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class LeaderboardUser extends User implements Comparable<LeaderboardUser> {
    private Integer total_point;
    private Integer total_attempt_count;
    private Long total_finish_time_duration;

    public LeaderboardUser() {}

    public LeaderboardUser(Integer total_point, Integer total_attempt_count, Long total_finish_time_duration) {
        this.total_point = total_point;
        this.total_attempt_count = total_attempt_count;
        this.total_finish_time_duration = total_finish_time_duration;
    }

    public LeaderboardUser(Integer user_id, String email, String password, String name, String phone_no, String description, LocalDateTime last_password_change, Integer total_point, Integer total_attempt_count, Long total_finish_time_duration) {
        super(user_id, email, password, name, phone_no, description, last_password_change);
        this.total_point = total_point;
        this.total_attempt_count = total_attempt_count;
        this.total_finish_time_duration = total_finish_time_duration;
    }

    public Integer getTotal_point() {
        return total_point;
    }

    public void setTotal_point(Integer total_point) {
        this.total_point = total_point;
    }

    public Integer getTotal_attempt_count() {
        return total_attempt_count;
    }

    public void setTotal_attempt_count(Integer total_attempt_count) {
        this.total_attempt_count = total_attempt_count;
    }

    public Long getTotal_finish_time_duration() {
        return total_finish_time_duration;
    }

    public void setTotal_finish_time_duration(Long total_finish_time_duration) {
        this.total_finish_time_duration = total_finish_time_duration;
    }

    public static LeaderboardUser sum(List<LeaderboardUser> leaderboardUserList) {
        Integer final_total_point = 0;
        Integer final_total_attempt_count = 0;
        Long final_total_finish_time_duration = 0L;

        for (LeaderboardUser leaderboardUser: leaderboardUserList) {
            final_total_point += leaderboardUser.getTotal_point();
            final_total_attempt_count += leaderboardUser.getTotal_attempt_count();
            final_total_finish_time_duration += leaderboardUser.getTotal_finish_time_duration();
        }
        LeaderboardUser leaderboardUser = new LeaderboardUser(final_total_point, final_total_attempt_count, final_total_finish_time_duration);
        leaderboardUser.setUser_id(leaderboardUserList.get(0).getUser_id());
        leaderboardUser.setName(leaderboardUserList.get(0).getName());

        return leaderboardUser;
    }

//    @Override
//    public int compareTo(LeaderboardUser o) {
//        if (this.total_point > o.total_point) {
//            return o.total_point - this.total_point;
//        } else {
//            if (this.total_attempt_count != o.total_attempt_count) {
//                return o.total_attempt_count - this.total_attempt_count;
//            } else {
//                if (this.total_finish_time_duration != o.total_finish_time_duration) {
//                    return (int)(o.total_finish_time_duration - this.total_finish_time_duration);
//                } else {
//                    return 0;
//                }
//            }
//        }
//    }

    @Override
    public int compareTo(LeaderboardUser o) {
        if (this.total_point > o.total_point) {
            return -1;
        } else if (this.total_point < o.total_point) {
            return 1;
        } else {
            if (this.total_attempt_count > o.total_attempt_count) {
                return 1;
            } else if (this.total_attempt_count < o.total_attempt_count) {
                return -1;
            } else {
                return this.total_finish_time_duration.compareTo(o.total_finish_time_duration);
            }
        }
    }
}
