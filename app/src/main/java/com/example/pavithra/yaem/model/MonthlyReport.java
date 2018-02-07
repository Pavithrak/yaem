package com.example.pavithra.yaem.model;

public class MonthlyReport {
    private Integer year;
    private Integer month;
    private Double totalCredit;
    private Double totalDebit;

    public MonthlyReport(Integer year, Integer month, Double totalCredit, Double totalDebit) {
        this.year = year;
        this.month = month;
        this.totalCredit = totalCredit;
        this.totalDebit = totalDebit;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(Double totalDebit) {
        this.totalDebit = totalDebit;
    }
}
