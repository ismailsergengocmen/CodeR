package com.backend.backend.repository;

import com.backend.backend.entity.CodingAttempt;
import com.backend.backend.entity.NonCodingAttempt;
import com.backend.backend.entity.SubmitResult;
import com.backend.backend.entity.TestCaseAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AttemptRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Integer> intMapper = (rs, rowNum) -> rs.getInt(1);

    RowMapper<TestCaseAttempt> testCaseAttemptMapper = (rs, rowNum) -> {
        TestCaseAttempt testCaseAttempt = new TestCaseAttempt();
        testCaseAttempt.setAttempt_id(rs.getInt("attempt_id"));
        testCaseAttempt.setTest_case_id(rs.getInt("test_case_id"));
        testCaseAttempt.setResult(rs.getString("result"));

        return testCaseAttempt;
    };

    RowMapper<CodingAttempt> codingAttemptMapper = (rs, rowNum) -> {
        CodingAttempt codingAttempt = new CodingAttempt();
        codingAttempt.setAttempt_id(rs.getInt("attempt_id"));
        //codingAttempt.setAttempt_start(rs.getTimestamp("attempt_start").toLocalDateTime());
        codingAttempt.setAttempt_end(rs.getTimestamp("attempt_end").toLocalDateTime());
        codingAttempt.setPoint(rs.getInt("point"));
        codingAttempt.setUser_id(rs.getInt("user_id"));
        codingAttempt.setProgramming_language(rs.getString("programming_language"));
        codingAttempt.setCode(rs.getString("code"));
        codingAttempt.setQuestion_id(rs.getInt("question_id"));

        return codingAttempt;
    };

    RowMapper<NonCodingAttempt> noncodingAttemptMapper = (rs, rowNum) -> {
        NonCodingAttempt nonCodingAttempt = new NonCodingAttempt();
        nonCodingAttempt.setAttempt_id(rs.getInt("attempt_id"));
        //nonCodingAttempt.setAttempt_start(rs.getTimestamp("attempt_start").toLocalDateTime());
        nonCodingAttempt.setAttempt_end(rs.getTimestamp("attempt_end").toLocalDateTime());
        nonCodingAttempt.setPoint(rs.getInt("point"));
        nonCodingAttempt.setUser_id(rs.getInt("user_id"));
        nonCodingAttempt.setAnswer(rs.getString("answer"));
        nonCodingAttempt.setQuestion_id(rs.getInt("question_id"));

        return nonCodingAttempt;
    };

    public SubmitResult submitChallenge(CodingAttempt codingAttempt) {
        LocalDateTime currentTime = LocalDateTime.now().withNano(0);

        try {
            // Insert into attempt table
            String sql = "INSERT INTO attempt (attempt_end) VALUES (?)";
            jdbcTemplate.update(sql, currentTime);

            // Get the newly inserted Attempt's attempt_id
            sql = "SELECT last_insert_id()";
            Integer attempt_id = jdbcTemplate.queryForObject(sql, Integer.class);

            // Insert into coding_attempt table
            sql = "INSERT INTO coding_attempt(attempt_id, programming_language, code) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, attempt_id, codingAttempt.getProgramming_language(), codingAttempt.getCode());

            // Insert into solve_coding table
            sql = "INSERT INTO solve_coding(user_id, question_id, attempt_id) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, codingAttempt.getUser_id(), codingAttempt.getQuestion_id(), attempt_id);

            // Insert into test_case_attempt table after checking the code for each test case
            // TODO SEND REQUEST TO COMPILER API
            sql = "SELECT test_case_id FROM test_case WHERE question_id = ?";
            List<Integer> test_case_ids = jdbcTemplate.query(sql, intMapper, codingAttempt.getQuestion_id());

            sql = "INSERT INTO test_case_attempt(attempt_id, test_case_id, result) VALUES (?, ?, ?)";
            for (Integer test_case_id: test_case_ids) {
                int randomValue = new Random().nextInt(10);
                // Randomly assign pass/fail to the test cases
                if (randomValue % 2 == 0) {
                    jdbcTemplate.update(sql, attempt_id, test_case_id, "pass");
                } else {
                    jdbcTemplate.update(sql, attempt_id, test_case_id, "fail");
                }
            }

            sql = "SELECT test_case_id, attempt_id, result FROM test_case_attempt WHERE attempt_id = ?";
            List<TestCaseAttempt> testCaseAttempt = jdbcTemplate.query(sql, testCaseAttemptMapper, attempt_id);

            int result = 0;
            for (TestCaseAttempt t: testCaseAttempt) {
                if (t.getResult().equals("pass")) {
                    result++;
                }
            }

            sql = "UPDATE attempt SET point = ? WHERE attempt_id = ?";
            int point = (100 * result) / testCaseAttempt.size();
            jdbcTemplate.update(sql, point, attempt_id);

            // Get the test_case_results from test_case_attempt table and return to the user
            sql = "SELECT attempt_id, test_case_id, result FROM test_case_attempt WHERE attempt_id = ?";
            List<TestCaseAttempt> testCaseAttempts = jdbcTemplate.query(sql, testCaseAttemptMapper, attempt_id);
            return new SubmitResult(testCaseAttempts, point);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean submitNonCodingQuestion(NonCodingAttempt nonCodingAttempt) {
        LocalDateTime currentTime = LocalDateTime.now().withNano(0);

         try {
             // Insert into attempt table
             String sql = "INSERT INTO attempt (attempt_end) VALUES (?)";
             jdbcTemplate.update(sql, currentTime);

             // Get the newly inserted Attempt's attempt_id
             sql = "SELECT last_insert_id()";
             Integer attempt_id = jdbcTemplate.queryForObject(sql, Integer.class);

             // Insert into non_coding_attempt table
             sql = "INSERT INTO non_coding_attempt (attempt_id, answer) VALUES (?, ?)";
             jdbcTemplate.update(sql, attempt_id, nonCodingAttempt.getAnswer());

             // Insert into solve_non_coding table
             sql = "INSERT INTO solve_non_coding (user_id, question_id, attempt_id) VALUES (?, ?, ?)";
             jdbcTemplate.update(sql, nonCodingAttempt.getUser_id(), nonCodingAttempt.getQuestion_id(), attempt_id);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<CodingAttempt> getPastChallengeAttempt(Integer question_id, Integer point) {
        try {
            String sql = "SELECT attempt_id, attempt_start, attempt_end, point, user_id, programming_language, code, question_id FROM attempt NATURAL JOIN coding_attempt NATURAL JOIN solve_coding WHERE question_id = ?";
            if (point != null) {
                sql = sql + " AND point = ?";
                return jdbcTemplate.query(sql, codingAttemptMapper, question_id, point);
            }
            return jdbcTemplate.query(sql, codingAttemptMapper, question_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<NonCodingAttempt> getPastNonCodingQuestionAttempts(Integer question_id) {
        try {
            String sql = "SELECT attempt_id, attempt_start, attempt_end, point, user_id, answer, question_id FROM attempt NATURAL JOIN non_coding_attempt NATURAL JOIN solve_non_coding WHERE question_id = ?";
            return jdbcTemplate.query(sql, noncodingAttemptMapper, question_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

