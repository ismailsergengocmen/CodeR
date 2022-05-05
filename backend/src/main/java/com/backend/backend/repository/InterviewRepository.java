package com.backend.backend.repository;


import com.backend.backend.entity.BasicQuestion;
import com.backend.backend.entity.Interview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public List<Interview> getAllInterviews() {
        String sql = "SELECT interview_id, title, start_date, duration, user_id FROM interview";
        List<Interview> interviewList = jdbcTemplate.query(sql, interviewMapper);
        return interviewList;
    }

    public Interview getInterviewWithId(Integer interview_id) {
        String sql = "SELECT interview_id, title, start_date, duration, user_id FROM interview WHERE interview_id = ?";
        try {
            Interview interview = jdbcTemplate.queryForObject(sql, interviewMapper, interview_id);


            // Add the interview questions
            sql = "SELECT question_id, user_id, question_difficulty, question_title FROM interview_has_question NATURAL JOIN question WHERE interview_id = ?";
            List<BasicQuestion> questions = jdbcTemplate.query(sql, basicQuestionMapper, interview_id);
            interview.setInterview_questions(questions);

            sql = "SELECT question_id FROM interview_has_question WHERE interview_id = ?";
            List<Integer> question_ids = jdbcTemplate.query(sql, intMapper, interview_id);//try
            interview.setQuestion_ids(question_ids);
            return interview;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer createInterview(Interview interview) {
        try {
            // Insert into interview table
            String sql = "INSERT INTO interview ( title, start_date, duration, user_id) VALUES ( ?, ?, ? , ?)";
            jdbcTemplate.update(sql,  interview.getTitle(), interview.getStart_date(), interview.getDuration(), interview.getUser_id());
            Integer interview_id = jdbcTemplate.queryForObject("SELECT interview_id FROM interview WHERE title = ? AND user_id = ? AND start_date = ? AND duration = ? ",  int.class, interview.getTitle(),  interview.getUser_id(), interview.getStart_date(), interview.getDuration());
            System.out.println(interview_id);
            // Insert interview into interview_has_question table
            sql = "INSERT INTO interview_has_question (interview_id, question_id) VALUES (?, ?)";
            for (Integer question_id : interview.getQuestion_ids()) {
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
            String sql = "SELECT interview_id, title, start_date, duration, user_id, FROM interview WHERE interview_id = ?";
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
            sql = "UPDATE interview SET user_id = ?, title = ?, start_date = ?,  duration = ? WHERE interview_id = ?";
            jdbcTemplate.update(sql, updatedInterview.getUser_id(), updatedInterview.getTitle(), updatedInterview.getStart_date(), updatedInterview.getDuration());

            if (interview.getQuestion_ids() != null) {
                // Delete the old questions and insert the new questions into interview_has_question table
                sql = "DELETE FROM interview_has_question WHERE interview_id = ?";
                jdbcTemplate.update(sql, interview.getInterview_id());

                sql = "INSERT INTO interview_has_question (interview_id, question_id) VALUES (?, ?)";
                for (Integer question_id : interview.getQuestion_ids()) {
                    jdbcTemplate.update(sql, interview.getInterview_id(), question_id);
                }

            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}