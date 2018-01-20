package com.example.pavithra.yaem.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.pavithra.yaem.persistence.TransactionAlert;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void add(List<TransactionAlert> transactions);

}
