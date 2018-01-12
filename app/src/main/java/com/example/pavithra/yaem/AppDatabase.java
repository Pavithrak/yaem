package com.example.pavithra.yaem;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.pavithra.yaem.dao.ExpenseReportDao;
import com.example.pavithra.yaem.dao.AccountDao;
import com.example.pavithra.yaem.persistence.Transaction;
import com.example.pavithra.yaem.persistence.Account;

@Database(entities = {Account.class,  Transaction.class
}, version = 1, exportSchema = false)
public abstract  class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract AccountDao accountDao();
    public abstract ExpenseReportDao expenseReportDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "yaem-db")
//                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
