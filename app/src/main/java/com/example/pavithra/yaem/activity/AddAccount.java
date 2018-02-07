package com.example.pavithra.yaem.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.adapter.AccountsAdapter;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.service.async.CreateAccount;
import com.example.pavithra.yaem.service.async.GetAccounts;
import com.example.pavithra.yaem.service.async.SyncExistingSms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddAccount extends AppCompatActivity {
    final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private AccountsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_account);
        requestSmsPermission();
        GetAccounts getAccounts = new GetAccounts(AppDatabase.getInstance(getApplicationContext()), this);
        getAccounts.execute();

    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    private void syncExistingSmsOnce() {
        boolean freshInstall;
        SharedPreferences sharedPreferences = getSharedPreferences("freshInstall", Context.MODE_PRIVATE);
        freshInstall = sharedPreferences.getBoolean("freshInstall", true);
        if (freshInstall) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("freshInstall", false);
            editor.commit();
            SyncExistingSms syncExistingSmsTask = new SyncExistingSms(AppDatabase.getInstance(getApplicationContext()), this);
            syncExistingSmsTask.execute();
        }
    }

    public void addAccount(View view) {
        EditText textView = findViewById(R.id.editText);
        String accountName = textView.getText().toString();
        if (accountName != null && accountName != "") {
            CreateAccount testSetUp = new CreateAccount(AppDatabase.getInstance(getApplicationContext()), this);
            testSetUp.execute(new Account(accountName));
        }
    }

    public void notifyAdapter(Account[] accounts) {
        adapter.addAll(accounts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    syncExistingSmsOnce();
                }
            }
        }
    }

    public void updateAccountsList(List<Account> accounts) {
        this.adapter = new AccountsAdapter(this, R.layout.account_row, accounts);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
