package com.example.pavithra.yaem.service.async;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.activity.AddAccount;
import com.example.pavithra.yaem.persistence.Account;

public class CreateAccount extends AsyncTask<Account, Void, Account[]> {
    AppDatabase appDatabase;
    AddAccount activity;

    public CreateAccount(AddAccount activity) {
        this.activity = activity;
        this.appDatabase = AppDatabase.getInstance(activity.getApplicationContext());
    }

    @Override
    protected void onPostExecute(Account[] accounts) {
        super.onPostExecute(accounts);
        Toast toast = Toast.makeText(activity.getApplicationContext(), "Account addded", Toast.LENGTH_LONG);
        toast.show();
        activity.newAccountsAdded(accounts);
    }

    @Override
    protected Account[] doInBackground(Account... accounts) {
        for(Account account : accounts) {
            appDatabase.accountDao().add(account);
        }
        return accounts;
    }
}
