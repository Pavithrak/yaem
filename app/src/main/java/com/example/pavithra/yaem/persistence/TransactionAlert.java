package com.example.pavithra.yaem.persistence;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
public class TransactionAlert {
    @PrimaryKey
    private Long id;
    private Long accountId;
    private String info;
    private Double debit;
    private Double credit;
    private Integer month;
    private Integer year;
    private Integer date;

    public TransactionAlert(Long id, Long accountId, String info, Double debit, Double credit,
                       Integer month, Integer year, Integer date) {
        this.id = id;
        this.accountId = accountId;
        this.info = info;
        this.debit = debit;
        this.credit = credit;
        this.month = month;
        this.year = year;
        this.date = date;
    }

    @Ignore
    public TransactionAlert(Long account, String info, Double debit, Double credit,
                            Integer month, Integer year, Integer date) {
        this.accountId = account;
        this.info = info;
        this.debit = debit;
        this.credit = credit;
        this.month = month;
        this.year = year;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getInfo() {
        return info;
    }

    public Double getDebit() {
        return debit;
    }

    public Double getCredit() {
        return credit;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setDate(Integer date) {
        this.date = date;
    }
}
