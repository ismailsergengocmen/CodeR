package com.backend.backend.repository;

import com.backend.backend.entity.Attempt;
import com.backend.backend.entity.BasicQuestion;
import com.backend.backend.entity.Contest;
import com.backend.backend.entity.LeaderboardUser;
import com.backend.backend.entity.Sponsor;
import com.backend.backend.util.IntWithString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ContestRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Contest> contestMapper = (rs, rowNum) -> {
        Contest contest = new Contest();
        contest.setContest_id(rs.getInt("contest_id"));
        contest.setUser_id(rs.getInt("user_id"));
        contest.setContest_name(rs.getString("contest_name"));
        contest.setDescription(rs.getString("description"));
        contest.setStart_time(rs.getTimestamp("start_time").toLocalDateTime());
        contest.setDuration(rs.getInt("duration"));
        contest.setCreate_date(rs.getTimestamp("create_date").toLocalDateTime());

        return contest;
    };

    RowMapper<IntWithString> categoryMapper = (rs, rowNum) -> {
        IntWithString intWithString = new IntWithString();
        intWithString.setInt_part(rs.getInt("contest_id"));
        intWithString.setString_part(rs.getString("category"));

        return intWithString;
    };

    RowMapper<String> stringMapper = (rs, rowNum) -> rs.getString(1);
    RowMapper<Integer> intMapper = (rs, rowNum) -> rs.getInt(1);

    RowMapper<Sponsor> sponsorMapper = (rs, rowNum) -> {
        Sponsor sponsor = new Sponsor();
        sponsor.setContest_id(rs.getInt("contest_id"));
        sponsor.setUser_id(rs.getInt("user_id"));
        sponsor.setMoney(rs.getInt("money"));
        sponsor.setSponsor_name(rs.getString("name"));

        return sponsor;
    };

    RowMapper<BasicQuestion> basicQuestionMapper = (rs, rowNum) -> {
        BasicQuestion question = new BasicQuestion();
        question.setQuestion_id(rs.getInt("question_id"));
        question.setUser_id(rs.getInt("user_id"));
        question.setQuestion_difficulty(rs.getInt("question_difficulty"));
        question.setQuestion_title(rs.getString("question_title"));

        return question;
    };

    RowMapper<Attempt> basicAttemptMapper = (rs, rowNum) -> {
        Attempt attempt = new Attempt();
        attempt.setAttempt_id(rs.getInt("attempt_id"));
        attempt.setUser_id(rs.getInt("user_id"));
        attempt.setPoint(rs.getInt("point"));
        attempt.setAttempt_end(rs.getTimestamp("attempt_end").toLocalDateTime());

        return attempt;
    };

    RowMapper<LeaderboardUser> leaderboardUserMapper = (rs, rowNum) -> {
        LeaderboardUser leaderboardUser = new LeaderboardUser();
        leaderboardUser.setUser_id(rs.getInt("user_id"));
        leaderboardUser.setName(rs.getString("name"));
        leaderboardUser.setTotal_point(rs.getInt("point"));
        leaderboardUser.setAttempt_end(rs.getTimestamp("attempt_end").toLocalDateTime());
        leaderboardUser.setTotal_finish_time_duration(0L); //TODO rs.getTimestamp("finish_time")

        return leaderboardUser;
    };

    public List<Contest> getAllContests(Integer user_id, boolean entered) {
        List<Contest> contestList;
        String sql = "SELECT contest.contest_id, contest.user_id, contest_name, description, start_time, duration, create_date FROM contest";
        if (user_id != null) {
            if (entered) {
                sql += " INNER JOIN enter_contest EC ON contest.contest_id = EC.contest_id WHERE EC.user_id = ?";
            } else {
                sql = "SELECT contest_id, contest.user_id, contest_name, description, start_time, duration, create_date " +
                        "FROM contest " +
                        "WHERE contest_id NOT IN (SELECT contest.contest_id " +
                                                    "FROM contest INNER JOIN enter_contest EC ON contest.contest_id = EC.contest_id " +
                                                    "WHERE EC.user_id = ?)";
            }
            contestList = jdbcTemplate.query(sql, contestMapper, user_id);
        }
        else {
            contestList = jdbcTemplate.query(sql, contestMapper);
        }

        HashMap<Integer, List<String>> categories = categoryHashMapper();
        HashMap<Integer, List<Sponsor>> sponsors = sponsorHashMapper();
        for (Contest c: contestList) {
            c.setCategory(categories.get(c.getContest_id()));
            c.setSponsors(sponsors.get(c.getContest_id()));
        }

        return contestList;
    }

    public Contest getContestWithId(int contest_id) {
        String sql = "SELECT contest_id, user_id, contest_name, description, start_time, duration, create_date FROM contest WHERE contest_id = ?";
         try {
             Contest contest = jdbcTemplate.queryForObject(sql, contestMapper, contest_id);

             // Add the contest categories
             sql = "SELECT category FROM contest_category WHERE contest_id = ?";
             List<String> categories = jdbcTemplate.query(sql, stringMapper, contest_id);
             contest.setCategory(categories);

             // Add the contest questions
             sql = "SELECT question_id, user_id, question_difficulty, question_title FROM contest_has_question NATURAL JOIN question WHERE contest_id = ?";
             List<BasicQuestion> questions = jdbcTemplate.query(sql, basicQuestionMapper, contest_id);
             contest.setContest_questions(questions);

             sql = "SELECT question_id FROM contest_has_question WHERE contest_id = ?";
             List<Integer> question_ids = jdbcTemplate.query(sql, intMapper, contest_id);
             contest.setQuestion_ids(question_ids);

             // Add the contests sponsors
             sql = "SELECT contest_id, user_id, money, name FROM sponsor NATURAL JOIN user WHERE contest_id = ? ";
             List<Sponsor> sponsors = jdbcTemplate.query(sql, sponsorMapper, contest_id);
             contest.setSponsors(sponsors);

             return contest;
         } catch (EmptyResultDataAccessException e) {
             System.out.println(e.getMessage());
             return null;
         }
    }

    public Integer createContest(Contest contest) {
        try {
            // Insert into contest table
            String sql = "INSERT INTO contest (contest_name, user_id, description, start_time, duration, create_date) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, contest.getContest_name(), contest.getUser_id(), contest.getDescription(), contest.getStart_time(), contest.getDuration(), contest.getCreate_date());

            int contest_id = jdbcTemplate.queryForObject("SELECT contest_id FROM contest WHERE user_id = ? AND create_date = ?", int.class, contest.getUser_id(), contest.getCreate_date());

            // Insert categories into contest_category table
            sql = "INSERT INTO contest_category (contest_id, category) VALUES (?, ?)";
            for (String category: contest.getCategory()) {
                jdbcTemplate.update(sql, contest_id, category);
            }

            // Insert questions into contest_has_question table
            sql = "INSERT INTO contest_has_question (contest_id, question_id) VALUES (?, ?)";
            for(Integer question_id: contest.getQuestion_ids()) {
                jdbcTemplate.update(sql, contest_id, question_id);
            }

            // Insert sponsors into sponsor table
            sql = "INSERT INTO sponsor (user_id, contest_id, money) VALUES (?, ?, ?)";
            for(Sponsor sponsor: contest.getSponsors()) {
                jdbcTemplate.update(sql, sponsor.getUser_id(), contest_id, sponsor.getMoney());
            }

            return contest_id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean addParticipant(int user_id, int contest_id) {
        try {
            String sql = "INSERT INTO enter_contest (user_id, contest_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, user_id, contest_id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean updateContest(Contest contest) {
        try {
            String sql = "SELECT user_id, contest_id, contest_name, description, start_time, duration, create_date FROM contest WHERE contest_id = ?";
            Contest currentContest = jdbcTemplate.queryForObject(sql, contestMapper, contest.getContest_id());

            Contest updatedContest = new Contest();
            updatedContest.setContest_id(contest.getContest_id());
            updatedContest.setUser_id(currentContest.getUser_id());
            updatedContest.setContest_name(currentContest.getContest_name());
            updatedContest.setDescription(currentContest.getDescription());
            updatedContest.setStart_time(currentContest.getStart_time());
            updatedContest.setDuration(currentContest.getDuration());

            if (contest.getUser_id() != null) {
                updatedContest.setUser_id(contest.getUser_id());
            }
            if(contest.getContest_name() != null) {
                updatedContest.setContest_name(contest.getContest_name());
            }
            if (contest.getDescription() != null) {
                updatedContest.setDescription(contest.getDescription());
            }
            if (contest.getStart_time() != null) {
                updatedContest.setStart_time(contest.getStart_time());
            }
            if (contest.getDuration() != null) {
                updatedContest.setDuration(contest.getDuration());
            }

            // Update the contest table
            sql = "UPDATE contest SET user_id = ?, contest_name = ?, description = ?, start_time = ?, duration = ? WHERE contest_id = ?";
            jdbcTemplate.update(sql, updatedContest.getUser_id(), updatedContest.getContest_name(), updatedContest.getDescription(), updatedContest.getStart_time(), updatedContest.getDuration(), updatedContest.getContest_id());

            if (contest.getCategory() != null) {
                // Delete the old categories and insert the new categories into contest_category table
                sql = "DELETE FROM contest_category WHERE contest_id = ?";
                jdbcTemplate.update(sql, contest.getContest_id());

                sql = "INSERT INTO contest_category (contest_id, category) VALUES (?, ?)";
                for (String category: contest.getCategory()) {
                    jdbcTemplate.update(sql, contest.getContest_id(), category);
                }
            }
            if (contest.getQuestion_ids() != null) {
                // Delete the old questions and insert the new questions into contest_has_question table
                sql = "DELETE FROM contest_has_question WHERE contest_id = ?";
                jdbcTemplate.update(sql, contest.getContest_id());

                sql = "INSERT INTO contest_has_question (contest_id, question_id) VALUES (?, ?)";
                for(Integer question_id: contest.getQuestion_ids()) {
                    jdbcTemplate.update(sql, contest.getContest_id(), question_id);
                }
            }

            if (contest.getSponsors() != null) {
                // Delete the old sponsors and insert the new sponsors into sponsor table
                sql = "DELETE FROM sponsor WHERE contest_id = ?";
                jdbcTemplate.update(sql, contest.getContest_id());

                sql = "INSERT INTO sponsor (user_id, contest_id, money) VALUES (?, ?, ?)";
                for(Sponsor sponsor: contest.getSponsors()) {
                    jdbcTemplate.update(sql, sponsor.getUser_id(), contest.getContest_id(), sponsor.getMoney());
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public HashMap<Integer, List<String>> categoryHashMapper() { //TODO
        HashMap<Integer, List<String>> all_items_map = new HashMap<>();
        List<IntWithString> all_items_list;

        String sql = "SELECT contest_id, category FROM contest_category";
        all_items_list = jdbcTemplate.query(sql, categoryMapper);

        for (IntWithString i: all_items_list) {
            if (all_items_map.containsKey(i.getInt_part())) {
                all_items_map.get(i.getInt_part()).add(i.getString_part());
            } else {
                all_items_map.put(i.getInt_part(), new ArrayList<>());
                all_items_map.get(i.getInt_part()).add(i.getString_part());
            }
        }
        return all_items_map;
    }

    public HashMap<Integer, List<Sponsor>> sponsorHashMapper() { //TODO
        HashMap<Integer, List<Sponsor>> all_items_map = new HashMap<>();
        List<Sponsor> all_items_list;

        String sql = "SELECT contest_id, user_id, money, name FROM sponsor NATURAL JOIN user";
        all_items_list = jdbcTemplate.query(sql, sponsorMapper);

        for (Sponsor i: all_items_list) {
            if (all_items_map.containsKey(i.getUser_id())) {
                all_items_map.get(i.getContest_id()).add(new Sponsor(i.getContest_id(), i.getSponsor_name(), i.getUser_id(), i.getMoney()));
            } else {
                all_items_map.put(i.getContest_id(), new ArrayList<>());
                all_items_map.get(i.getContest_id()).add(new Sponsor(i.getContest_id(), i.getSponsor_name(), i.getUser_id(), i.getMoney()));
            }
        }
        return all_items_map;
    }

    public List<LeaderboardUser> getLeaderboard(Integer contest_id) {
        // Find the participants' best attempts for each question. This will return question_count x participant_count amount of entry.
        String sql = "SELECT SC1.user_id, SC1.question_id, U1.name, point, A1.attempt_end " +
                        "FROM attempt A1 INNER JOIN coding_attempt CA1 ON A1.attempt_id = CA1.attempt_id " +
                        "INNER JOIN solve_coding SC1 ON A1.attempt_id = SC1.attempt_id " +
                        "INNER JOIN user U1 ON SC1.user_id = U1.user_id " +
                        "WHERE SC1.user_id IN (SELECT user_id FROM enter_contest WHERE contest_id = ?) " +
                        "AND SC1.question_id IN (SELECT question_id FROM contest_has_question WHERE contest_id = ?) " +
                        "AND SC1.attempt_id = (SELECT SC2.attempt_id " +
                                                "FROM attempt NATURAL JOIN solve_coding SC2 NATURAL JOIN coding_attempt " +
                                                "WHERE SC2.user_id = SC1.user_id AND SC2.question_id = SC1.question_id " +
                                                "ORDER BY point DESC, attempt_end DESC " +
                                                "LIMIT 1)";

        try {
            List<LeaderboardUser> questionPoints = jdbcTemplate.query(sql, leaderboardUserMapper, contest_id, contest_id);

            sql = "SELECT start_time FROM contest WHERE contest_id = ?";
            LocalDateTime contest_start_date = jdbcTemplate.queryForObject(sql, LocalDateTime.class, contest_id);

            HashMap<Integer, LeaderboardUser> leaderboardUserHashMap = new HashMap<>();
            List<LeaderboardUser> leaderboardUsers = new ArrayList<>();

            for (LeaderboardUser leaderboardUser: questionPoints) {
                if (!leaderboardUserHashMap.containsKey(leaderboardUser.getUser_id())) {
                    Long max_point_duration = Duration.between(contest_start_date, leaderboardUser.getAttempt_end()).toSeconds();
                    leaderboardUser.setTotal_finish_time_duration(max_point_duration);
                    leaderboardUserHashMap.put(leaderboardUser.getUser_id(), leaderboardUser);
                } else {
                    LeaderboardUser addedLeaderboardUser = new LeaderboardUser();
                    sql = "SELECT COUNT(*) " +
                            "FROM attempt NATURAL JOIN coding_attempt NATURAL JOIN solve_coding " +
                            "WHERE question_id IN (SELECT question_id FROM contest_has_question WHERE contest_id = ? AND user_id = ?)";

                    Integer total_attempt_count = jdbcTemplate.queryForObject(sql, int.class, contest_id, leaderboardUser.getUser_id());

                    Integer user_id = leaderboardUser.getUser_id();
                    addedLeaderboardUser.setUser_id(user_id);
                    addedLeaderboardUser.setName(leaderboardUser.getName());
                    addedLeaderboardUser.setTotal_attempt_count(total_attempt_count);
                    addedLeaderboardUser.setTotal_point(leaderboardUser.getTotal_point() + leaderboardUserHashMap.get(user_id).getTotal_point());
                    addedLeaderboardUser.setTotal_finish_time_duration(leaderboardUser.getTotal_finish_time_duration() + leaderboardUserHashMap.get(user_id).getTotal_finish_time_duration());

                    leaderboardUsers.add(addedLeaderboardUser);
                }
            }

            return leaderboardUsers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
