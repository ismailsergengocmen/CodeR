package com.backend.backend.repository;

import com.backend.backend.entity.JobSeeker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<JobSeeker> rowMapper = (rs, rowNum) -> {
        JobSeeker jobseeker = new JobSeeker();
        jobseeker.setUser_id(rs.getInt("user_id"));
        jobseeker.setEmail(rs.getString("email"));
        jobseeker.setPassword(rs.getString("password"));
        jobseeker.setName(rs.getString("name"));
        jobseeker.setPhone_no(rs.getString("phone_no"));
        jobseeker.setDescription(rs.getString("description"));
        jobseeker.setLast_password_change(rs.getTimestamp("last_password_change").toLocalDateTime());
        jobseeker.setCv_url(rs.getString("cv_url"));

        return jobseeker;
    };

    public List<JobSeeker> getAllJobSeekers() {
        String sql = "SELECT user_id, email, password, name, phone_no, description, last_password_change, cv_url FROM job_seeker NATURAL JOIN user";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void addNewJobSeeker(int user_id) {
        String sql = "INSERT INTO job_seeker (user_id) VALUES (?)";
        jdbcTemplate.update(sql, user_id);
    }

    public JobSeeker findJobSeekersWithId(int user_id) {
        String sql = "SELECT user_id, email, password, name, phone_no, description, last_password_change, cv_url FROM job_seeker NATURAL JOIN user WHERE user_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, user_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Boolean updateCvUrl(JobSeeker jobSeeker) {
        String sql = "UPDATE job_seeker SET cv_url = ? WHERE user_id = ?";

        try {
            jdbcTemplate.update(sql, jobSeeker.getCv_url(), jobSeeker.getUser_id());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
