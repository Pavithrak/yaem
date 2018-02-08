package com.example.pavithra.yaem.activity;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.adapter.AccountsAdapter;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.service.async.CreateAccount;
import com.example.pavithra.yaem.service.async.GetAccounts;
import com.example.pavithra.yaem.service.async.SyncExistingSms;

import java.util.List;


public class AddAccountActivity extends AppCompatActivity {
    final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private AccountsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_account);
        requestSmsPermission();
        GetAccounts getAccounts = new GetAccounts(this);
        getAccounts.execute();

    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    private void syncExistingSms(Account[] accounts) {
            SyncExistingSms syncExistingSmsTask = new SyncExistingSms(this);
            syncExistingSmsTask.execute(accounts);
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
        this.adapter = new AccountsAdapter(this, R.layout.account_row, accounts);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
