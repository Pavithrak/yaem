package com.example.pavithra.yaem.service.async;

import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.activity.AddAccountActivity;
import com.example.pavithra.yaem.persistence.Account;

import java.util.List;

public class GetAccounts extends AsyncTask<Void, Void, List<Account>> {
    AppDatabase appDatabase;
    AddAccountActivity activity;

    public GetAccounts(AddAccountActivity activity) {
        this.activity = activity;
        this.appDatabase = AppDatabase.getInstance(activity.getApplicationContext());
    }

    @Override
    protected void onPostExecute(List<Account> accounts) {
        super.onPostExecute(accounts);
        activity.updateAccountsList(accounts);

    }

    @Override
    protected List<Account> doInBackground(Void... voids) {
        return appDatabase.accountDao().getAccounts();
    }
}
