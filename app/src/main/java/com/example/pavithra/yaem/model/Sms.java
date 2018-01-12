package com.example.pavithra.yaem.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sms {
    private String address;
    private String body;
    private Date date;
    private final List<String> withdrawalRegex = new ArrayList<>();

    public Sms(String address, String body, Date date) {
        this.address = address;
        this.body = body;
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "address='" + address + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }

    public Double getWithdrawlAmount() {
        return null;
    }
}
