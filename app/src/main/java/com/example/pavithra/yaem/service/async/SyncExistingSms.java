package com.example.pavithra.yaem.service.async;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.SmsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SyncExistingSms extends AsyncTask<Account, Void, List> {
    private AppCompatActivity activity;
    private AppDatabase appDatabase;

    public SyncExistingSms(AppCompatActivity activity) {
        this.activity = activity;
        this.appDatabase = AppDatabase.getInstance(activity.getApplicationContext());
    }

    public SyncExistingSms(AppCompatActivity activity, AppDatabase appDatabase) {
        this.activity = activity;
        this.appDatabase = appDatabase;
    }

    @Override
    protected List<Sms> doInBackground(Account... accounts) {
        List<Sms> sms = this.readExistingSms();
        List<TransactionAlert> transactionAlerts = getSmsService(sms, accounts).getFilteredTransactionAlerts();
        saveTransactionAlerts(transactionAlerts);
        return sms;
    }


    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        for (Object obj : list) {
            Sms sms = (Sms) obj;
            if (!sms.isATransactionSms()) {
                System.out.println("This invalid sms is " + sms);
            }
        }
    }

    void saveTransactionAlerts(List<TransactionAlert> transactionAlerts) {
        appDatabase.transactionDao().add(transactionAlerts);
    }

    SmsService getSmsService(List<Sms> sms, Account[] accounts) {
        return new SmsService(sms, Arrays.asList(accounts));
    }

    List<Sms> readExistingSms() {
        List<Sms> sms = new ArrayList();
        String[] projection = new String[]{"_id", "address", "body", "date"};
        Cursor cur = activity.getContentResolver().query(Uri.parse("content://sms/"), projection, null, null, "date desc");
        if (cur.moveToFirst()) {
            int index_Address = cur.getColumnIndex("address");
            int index_Body = cur.getColumnIndex("body");
            int index_Date = cur.getColumnIndex("date");
            do {
                sms.add(new Sms(cur.getString(index_Address), cur.getString(index_Body), new Date(cur.getLong(index_Date))));

            } while (cur.moveToNext());
            if (!cur.isClosed()) {
                cur.close();
                cur = null;
            }
        }
        return sms;
    }
}
