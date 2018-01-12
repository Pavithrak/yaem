package com.example.pavithra.yaem.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.pavithra.yaem.persistence.Transaction;

@Dao
public interface ExpenseReportDao {
    @Insert
    void add(Transaction report);

}
