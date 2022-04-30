package com.backend.backend.util;

import java.sql.Date;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

public class Utilities {
    static public LocalDate getLocalDate(ResultSet rs, String columnName) throws SQLException {
        Date sqlDate = rs.getDate(columnName);
        return sqlDate == null ? null : sqlDate.toLocalDate();
    }
}
