package com.example.pavithra.yaem.model;

import java.util.Date;

public class Sms {
    private String address;
    private String body;
    private Date date;

    public Sms(String address, String body, Date date) {
        this.address = address;
        this.body = body;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "address='" + address + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }
}
