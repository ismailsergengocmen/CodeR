package com.backend.backend.repository;

import com.backend.backend.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Company> rowMapper = (rs, rowNum) -> {
        Company company = new Company();
        company.setUser_id(rs.getInt("user_id"));
        company.setEmail(rs.getString("email"));
        company.setPassword(rs.getString("password"));
        company.setName(rs.getString("name"));
        company.setPhone_no(rs.getString("phone_no"));
        company.setDescription(rs.getString("description"));

        return company;
    };

    public List<Company> getAllCompanies() {
        String sql = "SELECT user_id, email, password, name, phone_no, description FROM company NATURAL JOIN user";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void addNewCompany(int user_id) {
        String sql = "INSERT INTO company (user_id) VALUES (?)";
        jdbcTemplate.update(sql, user_id);
    }

    public Company findCompanyWithId(int user_id) {
        String sql = "SELECT user_id, email, password, name, phone_no, description FROM company NATURAL JOIN user WHERE user_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, user_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
