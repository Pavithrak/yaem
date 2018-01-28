package com.example.pavithra.yaem.model.tasks.db;

import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.persistence.Account;

public class CreateAccount extends AsyncTask<Account, Void, Void> {
    AppDatabase appDatabase;

    public CreateAccount(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    protected Void doInBackground(Account... accounts) {
        for(Account account : accounts) {
            appDatabase.accountDao().add(account);
        }
        return null;
    }
}
