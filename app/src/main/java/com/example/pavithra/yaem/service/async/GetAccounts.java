package com.example.pavithra.yaem.service.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.persistence.Account;

import java.util.ArrayList;
import java.util.List;

public class GetAccounts extends AsyncTask<Void, Void, List<Account>> {
    AppDatabase appDatabase;
    AppCompatActivity activity;

    public GetAccounts(AppDatabase appDatabase, AppCompatActivity activity) {
        this.appDatabase = appDatabase;
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(List<Account> accounts) {
        super.onPostExecute(accounts);
        String[] accountNames = new String[accounts.size()];
        for(int i =0; i < accounts.size(); i++) {
            accountNames[i] = accounts.get(i).getName();
        }
        ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, accountNames);
        ListView listView = activity.findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    protected List<Account> doInBackground(Void... voids) {
        return appDatabase.accountDao().getAccounts();
    }
}
