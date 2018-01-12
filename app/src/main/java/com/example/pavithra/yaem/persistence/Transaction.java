package com.example.pavithra.yaem.persistence;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Transaction {
    @PrimaryKey
    private Long id;
    @Embedded(prefix = "account")
    private Account account;
    private String info;
    private Double debit;
    private Double credit;
//    private Date transactionDate;

    public Transaction(Account account, Double debit, Double credit, String info) {
        this.account = account;
        this.debit = debit;
        this.credit = credit;
        this.info = info;
//        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
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

//    public Date getTransactionDate() {
//        return transactionDate;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWalletId(Account account) {
        this.account = account;
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

//    public void setTransactionDate(Date transactionDate) {
//        this.transactionDate = transactionDate;
//    }
}
