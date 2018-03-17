package com.example.pavithra.yaem.service.async;

import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.persistence.Account;

import java.util.List;

public class GetAccounts extends AsyncTask<Void, Void, List<Account>> {
    AppDatabase appDatabase;
    AsyncTaskCallback callback;

    public GetAccounts(AsyncTaskCallback callback, AppDatabase appDatabase) {
        this.callback = callback;
        this.appDatabase = appDatabase;
    }

    @Override
    protected void onPostExecute(List<Account> accounts) {
        super.onPostExecute(accounts);
        callback.onSuccess(accounts);
    }

    @Override
    protected List<Account> doInBackground(Void... voids) {
        List<Account> accounts = appDatabase.accountDao().getAccounts();
        callback.onSuccessBackground(accounts);
        return accounts;
    }
}
