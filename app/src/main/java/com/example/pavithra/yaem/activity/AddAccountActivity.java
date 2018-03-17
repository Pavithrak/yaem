package com.example.pavithra.yaem.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.adapter.AccountsAdapter;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.service.async.CreateAccount;
import com.example.pavithra.yaem.service.async.DeleteAccount;
import com.example.pavithra.yaem.service.async.SyncExistingSms;

import java.util.List;


public class AddAccountActivity extends AppCompatActivity {
    private AccountsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        observeAccountsList();
    }

    public void addAccountClick(View view) {
        EditText textView = findViewById(R.id.editText);
        String accountName = textView.getText().toString();
        if (accountName != null && accountName != "") {
            CreateAccount createAccount = new CreateAccount(this);
            createAccount.execute(new Account(accountName));
        }
    }

    public void newAccountsAdded(Account[] accounts) {
        syncExistingSms(accounts);
        adapter.addAll(accounts);
        adapter.notifyDataSetChanged();
    }

    public void updateAccountsList(List<Account> accounts) {
        this.adapter = new AccountsAdapter(this, R.layout.account_row, accounts, new DeleteAccount(this));
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    private void observeAccountsList() {
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        LiveData<List<Account>> accounts = appDatabase.accountDao().getAccountsData();
        accounts.observe(this, new AccountsCallback());
    }


    private void syncExistingSms(Account[] accounts) {
        SyncExistingSms syncExistingSmsTask = new SyncExistingSms(this);
        syncExistingSmsTask.execute(accounts);
    }

    private class AccountsCallback implements Observer<List<Account>> {
        @Override
        public void onChanged(@Nullable List<Account> accounts) {
            updateAccountsList(accounts);
        }
    }

}
