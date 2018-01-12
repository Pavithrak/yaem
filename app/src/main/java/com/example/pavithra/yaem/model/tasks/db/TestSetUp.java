package com.example.pavithra.yaem.model.tasks.db;

import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.persistence.Account;

public class TestSetUp extends AsyncTask<Void, Void, Void> {
    AppDatabase appDatabase;

    public TestSetUp(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return this.addData();
    }

    private Void addData() {
        Account account = new Account("AM-FROMSC");
        appDatabase.accountDao().add(account);
        return null;
    }
}
