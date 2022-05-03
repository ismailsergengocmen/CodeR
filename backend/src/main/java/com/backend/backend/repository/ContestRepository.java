package com.backend.backend.repository;

import com.backend.backend.entity.Contest;
import com.backend.backend.util.Sponsor;
import com.backend.backend.util.IntWithString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    RowMapper<Sponsor> basicSponsorMapper = (rs, rowNum) -> {
        Sponsor sponsor = new Sponsor();
        sponsor.setUser_id(rs.getInt("user_id"));
        sponsor.setMoney(rs.getInt("money"));

        return sponsor;
    };

    RowMapper<Sponsor> sponsorMapper = (rs, rowNum) -> {
        Sponsor sponsor = new Sponsor();
        sponsor.setContest_id(rs.getInt("contest_id"));
        sponsor.setUser_id(rs.getInt("user_id"));
        sponsor.setMoney(rs.getInt("money"));

        return sponsor;
    };

    public List<Contest> getAllContests() {
        String sql = "SELECT contest_id, user_id, contest_name, description, start_time, duration, create_date FROM contest";
        List<Contest> contestList = jdbcTemplate.query(sql, contestMapper);

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
             sql = "SELECT question_id FROM contest_has_question WHERE contest_id = ?";
             List<Integer> questions = jdbcTemplate.query(sql, intMapper, contest_id);
             contest.setQuestion_ids(questions);

             // Add the contests sponsors
             sql = "SELECT user_id, money FROM sponsor WHERE contest_id = ?";
             List<Sponsor> sponsors = jdbcTemplate.query(sql, basicSponsorMapper, contest_id);
             contest.setSponsors(sponsors);

             return contest;
         } catch (EmptyResultDataAccessException e) {
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
            // Insert into contest table
            String sql = "UPDATE contest SET user_id = ?, contest_name = ?, description = ?, start_time = ?, duration = ? WHERE contest_id = ?";
            jdbcTemplate.update(sql, contest.getUser_id(), contest.getContest_name(), contest.getDescription(), contest.getStart_time(), contest.getDuration(), contest.getContest_id());

            // Delete the old categories and insert the new categories into contest_category table
            sql = "DELETE FROM contest_category WHERE contest_id = ?";
            jdbcTemplate.update(sql, contest.getContest_id());

            sql = "INSERT INTO contest_category (contest_id, category) VALUES (?, ?)";
            for (String category: contest.getCategory()) {
                jdbcTemplate.update(sql, contest.getContest_id(), category);
            }

            // Delete the old questions and insert the new questions into contest_has_question table
            sql = "DELETE FROM contest_has_question WHERE contest_id = ?";
            jdbcTemplate.update(sql, contest.getContest_id());

            sql = "INSERT INTO contest_has_question (contest_id, question_id) VALUES (?, ?)";
            for(Integer question_id: contest.getQuestion_ids()) {
                jdbcTemplate.update(sql, contest.getContest_id(), question_id);
            }

            // Delete the old sponsors and insert the new sponsors into sponsor table
            sql = "DELETE FROM sponsor WHERE contest_id = ?";
            jdbcTemplate.update(sql, contest.getContest_id());

            sql = "INSERT INTO sponsor (user_id, contest_id, money) VALUES (?, ?, ?)";
            for(Sponsor sponsor: contest.getSponsors()) {
                jdbcTemplate.update(sql, sponsor.getUser_id(), contest.getContest_id(), sponsor.getMoney());
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

        String sql = "SELECT contest_id, user_id, money FROM sponsor";
        all_items_list = jdbcTemplate.query(sql, sponsorMapper);

        for (Sponsor i: all_items_list) {
            if (all_items_map.containsKey(i.getUser_id())) {
                all_items_map.get(i.getContest_id()).add(new Sponsor(i.getContest_id(), i.getUser_id(), i.getMoney()));
            } else {
                all_items_map.put(i.getContest_id(), new ArrayList<>());
                all_items_map.get(i.getContest_id()).add(new Sponsor(i.getContest_id(), i.getUser_id(), i.getMoney()));
            }
        }
        return all_items_map;
    }

}
