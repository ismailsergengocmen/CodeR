package com.backend.backend.repository;

import com.backend.backend.entity.*;
import com.backend.backend.util.InterviewCodingQuestionResult;
import com.backend.backend.util.InterviewNonCodingQuestionResult;
import com.backend.backend.util.InterviewQuestionResult;
import com.backend.backend.util.InterviewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class InterviewRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    RowMapper<Interview> interviewMapper = (rs, rowNum) -> {
        Interview interview = new Interview();
        interview.setInterview_id(rs.getInt("interview_id"));
        interview.setTitle(rs.getString("title"));
        interview.setStart_date(rs.getTimestamp("start_date").toLocalDateTime());
        interview.setDuration(rs.getInt("duration"));
        interview.setCreate_date(rs.getTimestamp("create_date").toLocalDateTime());
        interview.setUser_id(rs.getInt("user_id"));
        return interview;
    };

    RowMapper<BasicQuestion> basicQuestionMapper = (rs, rowNum) -> {
        BasicQuestion question = new BasicQuestion();
        question.setQuestion_id(rs.getInt("question_id"));
        question.setUser_id(rs.getInt("user_id"));
        question.setQuestion_difficulty(rs.getInt("question_difficulty"));
        question.setQuestion_title(rs.getString("question_title"));

        return question;
    };

    RowMapper<Integer> intMapper = (rs, rowNum) -> rs.getInt(1);

    RowMapper<CodingAttempt> codingAttemptMapper = (rs, rowNum) -> {
        CodingAttempt attempt = new CodingAttempt();
        attempt.setAttempt_id(rs.getInt("attempt_id"));
        attempt.setUser_id(rs.getInt("user_id"));
        attempt.setPoint(rs.getInt("point"));
        attempt.setAttempt_end(rs.getTimestamp("attempt_end").toLocalDateTime());
        attempt.setCode(rs.getString("code"));
        attempt.setProgramming_language(rs.getString("programming_language"));

        return attempt;
    };

    RowMapper<NonCodingAttempt> basicNonCodingAttemptMapper = (rs, rowNum) -> {
        NonCodingAttempt attempt = new NonCodingAttempt();
        attempt.setAnswer(rs.getString("answer"));
        attempt.setAttempt_end(rs.getTimestamp("attempt_end").toLocalDateTime());

        return attempt;
    };

    public List<Interview> getAllInterviews(Integer company_id) {
        String sql = "SELECT interview_id, title, start_date, duration, create_date, user_id FROM interview";
        if (company_id != null) {
            sql += " WHERE user_id = ?";
            return jdbcTemplate.query(sql, interviewMapper, company_id);
        }
        return jdbcTemplate.query(sql, interviewMapper);
    }

    public Interview getInterviewWithId(Integer interview_id) {
        String sql = "SELECT interview_id, title, start_date, duration, create_date, user_id FROM interview WHERE interview_id = ?";
        try {
            Interview interview = jdbcTemplate.queryForObject(sql, interviewMapper, interview_id);

            // Add the interview questions
            sql = "SELECT question_id, user_id, question_difficulty, question_title FROM interview_has_question NATURAL JOIN question WHERE interview_id = ?";
            List<BasicQuestion> questions = jdbcTemplate.query(sql, basicQuestionMapper, interview_id);
            interview.setInterview_questions(questions);

            sql = "SELECT question_id FROM interview_has_question WHERE interview_id = ?";
            List<Integer> question_ids = jdbcTemplate.query(sql, intMapper, interview_id);//try
            interview.setInterview_question_ids(question_ids);

            return interview;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer createInterview(Interview interview) {
        try {
            // Insert into interview table
            String sql = "INSERT INTO interview (title, start_date, duration, user_id, create_date) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,  interview.getTitle(), interview.getStart_date(), interview.getDuration(), interview.getUser_id(), interview.getCreate_date());

            int interview_id = jdbcTemplate.queryForObject("SELECT interview_id FROM interview WHERE user_id = ? AND create_date = ?", int.class, interview.getUser_id(), interview.getCreate_date());

            // Insert interview into interview_has_question table
            sql = "INSERT INTO interview_has_question (interview_id, question_id) VALUES (?, ?)";
            for (Integer question_id: interview.getInterview_question_ids()) {
                jdbcTemplate.update(sql, interview_id, question_id);
            }
            return interview_id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean addJobSeeker(Integer user_id, Integer interview_id) {
        try {
            String sql = "INSERT INTO enter_interview (user_id, interview_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, user_id, interview_id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean updateInterview(Interview interview) {
        try {
            String sql = "SELECT interview_id, title, start_date, duration, user_id, create_date FROM interview WHERE interview_id = ?";
            Interview currentInterview = jdbcTemplate.queryForObject(sql, interviewMapper, interview.getInterview_id());

            Interview updatedInterview = new Interview();
            updatedInterview.setInterview_id(interview.getInterview_id());
            updatedInterview.setUser_id(currentInterview.getUser_id());
            updatedInterview.setTitle(currentInterview.getTitle());
            updatedInterview.setStart_date(currentInterview.getStart_date());
            updatedInterview.setDuration(currentInterview.getDuration());

            if (interview.getUser_id() != null) {
                updatedInterview.setUser_id(interview.getUser_id());
            }
            if (interview.getTitle() != null) {
                updatedInterview.setTitle(interview.getTitle());
            }
            if (interview.getStart_date() != null) {
                updatedInterview.setStart_date(interview.getStart_date());
            }
            if (interview.getDuration() != null) {
                updatedInterview.setDuration(interview.getDuration());
            }

            // Update the interview table
            sql = "UPDATE interview SET user_id = ?, title = ?, start_date = ?, duration = ? WHERE interview_id = ?";
            jdbcTemplate.update(sql, updatedInterview.getUser_id(), updatedInterview.getTitle(), updatedInterview.getStart_date(), updatedInterview.getDuration(), updatedInterview.getInterview_id());

            if (interview.getInterview_question_ids() != null) {
                // Delete the old questions and insert the new questions into interview_has_question table
                sql = "DELETE FROM interview_has_question WHERE interview_id = ?";
                jdbcTemplate.update(sql, interview.getInterview_id());

                sql = "INSERT INTO interview_has_question (interview_id, question_id) VALUES (?, ?)";
                for (Integer question_id : interview.getInterview_question_ids()) {
                    jdbcTemplate.update(sql, interview.getInterview_id(), question_id);
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<InterviewResult> getInterviewResults(Integer interview_id) {
        try {
            // Get the interview question_ids
            String sql = "SELECT question_id FROM interview_has_question WHERE interview_id = ?";
            List<Integer> question_ids = jdbcTemplate.query(sql, intMapper, interview_id);

            // Get the interview participant_ids
            sql = "SELECT user_id FROM enter_interview WHERE interview_id = ?";
            List<Integer> participant_ids = jdbcTemplate.query(sql, intMapper, interview_id);

            // Get the interview_start_date
            sql = "SELECT start_date FROM interview WHERE interview_id = ?";
            LocalDateTime interview_start_date = jdbcTemplate.queryForObject(sql, LocalDateTime.class, interview_id);

            List<InterviewResult> interviewResultsList = new ArrayList<>();
            for (Integer participant_id: participant_ids) {
                sql = "SELECT name FROM user WHERE user_id = ?";
                String participant_name = jdbcTemplate.queryForObject(sql, String.class, participant_id);

                InterviewResult interviewResult = new InterviewResult(new ArrayList<>(), new ArrayList<>(), participant_id, participant_name);
                int question_count = 1;
                for (Integer question_id: question_ids) {
                    sql = "SELECT question_title FROM question WHERE question_id = ?";
                    String question_title = jdbcTemplate.queryForObject(sql, String.class, question_id);

                    // Check if it is coding or noncoding !!!
                    sql = "SELECT question_id FROM challenge WHERE question_id = ?";
                    List<Integer> id = jdbcTemplate.query(sql, intMapper, question_id);

                    if (!id.isEmpty()) {
                        int max_point = 0;
                        String max_code = "";
                        String max_pl = "";
                        LocalDateTime max_point_attempt_end = null;
                        String question_id_name = "Q"+ question_count;
                        String status = "";

                        sql = "SELECT attempt_id, user_id, point, attempt_end, code, programming_language " +
                                "FROM attempt NATURAL JOIN solve_coding NATURAL JOIN coding_attempt " +
                                "WHERE user_id = ? AND question_id = ? " +
                                "ORDER BY point DESC, attempt_end DESC " +
                                "LIMIT 1";
                        List<CodingAttempt> attempt = jdbcTemplate.query(sql, codingAttemptMapper, participant_id, question_id);

                        if (!attempt.isEmpty()) {
                            max_point = attempt.get(0).getPoint();
                            max_code = attempt.get(0).getCode();
                            max_pl = attempt.get(0).getProgramming_language();
                            max_point_attempt_end = attempt.get(0).getAttempt_end();
                            status = (max_point == 100 ? "Successful" : "Not Successful");
                        } else {
                            status = "Not attempted";
                        }
                        Long max_point_duration = 0L; // TODO

                        InterviewCodingQuestionResult questionResult = new InterviewCodingQuestionResult(question_id_name, question_title, "challenge", max_point_duration, max_point, status, max_code, max_pl);
                        interviewResult.getInterviewCodingQuestionResults().add(questionResult);
                    } else {
                        String answer = "";
                        LocalDateTime max_point_attempt_end = null;
                        String question_id_name = "Q"+ question_count;
                        String status = "";

                        sql = "SELECT answer, attempt_end FROM attempt NATURAL JOIN solve_non_coding NATURAL JOIN non_coding_attempt WHERE user_id = ? AND question_id = ? ORDER BY attempt_end DESC LIMIT 1";
                        List<NonCodingAttempt> attempt = jdbcTemplate.query(sql, basicNonCodingAttemptMapper, participant_id, question_id);

                        if (!attempt.isEmpty()) {
                            answer = attempt.get(0).getAnswer();
                            max_point_attempt_end = attempt.get(0).getAttempt_end();
                            status = "Answered";
                        } else {
                            status = "Not answered";
                        }
                        Long max_point_duration = 0L; // TODO

                        InterviewNonCodingQuestionResult questionResult = new InterviewNonCodingQuestionResult(question_id_name, question_title, "noncoding", max_point_duration, 100, status, answer);
                        interviewResult.getInterviewNonCodingQuestionResults().add(questionResult);
                    }
                    question_count++;
                }
                interviewResultsList.add(interviewResult);
            }
            return interviewResultsList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}