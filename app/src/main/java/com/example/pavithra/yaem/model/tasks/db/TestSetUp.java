package com.example.pavithra.yaem.model.tasks.db;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.tasks.AsynchronousTask;
import com.example.pavithra.yaem.persistence.Account;

public class TestSetUp implements AsynchronousTask<Void> {
    @Override
    public Void execute(AppDatabase appDatabase) {
        Account account = new Account("AD-FROMSC");
        appDatabase.accountDao().add(account);
        return null;
    }
}
