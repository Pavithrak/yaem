package com.example.pavithra.yaem.model;

import java.io.Serializable;

public class MonthYear implements Serializable {
    private Integer year;
    private Integer month;

    public MonthYear(Integer year, Integer month) {
        this.year = year;
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }
}
