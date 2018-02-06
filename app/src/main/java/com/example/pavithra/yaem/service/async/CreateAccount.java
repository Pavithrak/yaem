package com.example.pavithra.yaem.service.async;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.persistence.Account;

public class CreateAccount extends AsyncTask<Account, Void, Void> {
    AppDatabase appDatabase;
    AppCompatActivity activity;

    public CreateAccount(AppDatabase appDatabase, AppCompatActivity activity) {
        this.appDatabase = appDatabase;
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast toast = Toast.makeText(activity.getApplicationContext(), "Account addded", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected Void doInBackground(Account... accounts) {
        for(Account account : accounts) {
            appDatabase.accountDao().add(account);
        }
        return null;
    }
}
