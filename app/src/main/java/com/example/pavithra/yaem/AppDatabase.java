package com.example.pavithra.yaem;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.pavithra.yaem.dao.TransactionDao;
import com.example.pavithra.yaem.dao.AccountDao;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.persistence.Account;

@Database(entities = {Account.class,  TransactionAlert.class
}, version = 4, exportSchema = false)
public abstract  class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract AccountDao accountDao();
    public abstract TransactionDao transactionDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "yaem-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
