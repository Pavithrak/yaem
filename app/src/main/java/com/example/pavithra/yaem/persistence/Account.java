package com.example.pavithra.yaem.persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class Account {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name;
    private Long number;

    public Account(String name, Long number) {
        this.name = name;
        this.number = number;
    }

    @Ignore
    public Account(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getNumber() {
        return number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
