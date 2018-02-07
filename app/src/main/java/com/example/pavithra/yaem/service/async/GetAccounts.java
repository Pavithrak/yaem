package com.example.pavithra.yaem.service.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.activity.AddAccount;
import com.example.pavithra.yaem.persistence.Account;

import java.util.ArrayList;
import java.util.List;

public class GetAccounts extends AsyncTask<Void, Void, List<Account>> {
    AppDatabase appDatabase;
    AddAccount activity;

    public GetAccounts(AppDatabase appDatabase, AddAccount activity) {
        this.appDatabase = appDatabase;
        this.activity = activity;
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
