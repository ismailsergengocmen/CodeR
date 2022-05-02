package com.backend.backend.util;

public class IntWithString {
    private Integer int_part;
    private String string_part;

    public IntWithString() {}

    public IntWithString(Integer int_part, String string_part) {
        this.int_part = int_part;
        this.string_part = string_part;
    }

    public Integer getInt_part() {
        return int_part;
    }

    public void setInt_part(Integer int_part) {
        this.int_part = int_part;
    }

    public String getString_part() {
        return string_part;
    }

    public void setString_part(String string_part) {
        this.string_part = string_part;
    }
}
