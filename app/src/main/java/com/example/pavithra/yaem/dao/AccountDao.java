package com.example.pavithra.yaem.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.pavithra.yaem.persistence.Account;

import java.util.List;

@Dao
public interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Account account);

    @Query("delete from Account where id = :id")
    void delete(long id);

    @Query("select name from Account")
    List<String> getAccountNames();
}
