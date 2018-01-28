package com.example.pavithra.yaem.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.model.tasks.sms.SyncExistingSms;


public class AddAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
//        CreateAccount testSetUp = new CreateAccount(AppDatabase.getInstance(getApplicationContext()));
//        testSetUp.execute();
//        syncExistingSmsOnce();
    }

    private void syncExistingSmsOnce() {
        boolean freshInstall;
        SharedPreferences sharedPreferences = getSharedPreferences("freshInstall", Context.MODE_PRIVATE);
        freshInstall = sharedPreferences.getBoolean("freshInstall", true);
        if (freshInstall) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("freshInstall", false);
            editor.commit();
            SyncExistingSms syncExistingSmsTask = new SyncExistingSms(AppDatabase.getInstance(getApplicationContext()), getContentResolver());
            syncExistingSmsTask.execute();
        }
    }

    public void addAccount(View view) {
        //        CreateAccount testSetUp = new CreateAccount(AppDatabase.getInstance(getApplicationContext()));
//        testSetUp.execute();

    }
}
