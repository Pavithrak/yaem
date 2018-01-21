package com.example.pavithra.yaem.model.tasks.sms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.SmsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SyncExistingSms extends AsyncTask<Void, Void, List> {
    private ContentResolver contentResolver;
    private AppDatabase appDatabase;
    private List<Account> accounts;

    public SyncExistingSms(AppDatabase appDatabase, ContentResolver contentResolver) {
        this.appDatabase = appDatabase;
        this.contentResolver = contentResolver;
    }

    @Override
    protected List<Sms> doInBackground(Void... voids) {
        this.accounts = appDatabase.accountDao().getAccounts();
        List<Sms> sms = this.readExistingSms();
        List<TransactionAlert> transactionAlerts = new SmsService(sms, accounts).getFilteredTransactionAlerts();
        saveTransactionAlerts(transactionAlerts);
        return sms;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        for(Object obj : list) {
            Sms sms = (Sms) obj;
            if (!sms.isATransactionSms()) {
                System.out.println("This invalid sms is " + sms);
            }
        }
    }

    void saveTransactionAlerts(List<TransactionAlert> transactionAlerts) {
        appDatabase.transactionDao().add(transactionAlerts);
    }

    List<Sms> readExistingSms() {
        List<Sms> sms = new ArrayList();
        String[] projection = new String[]{"_id", "address", "body", "date"};
        Cursor cur = contentResolver.query(Uri.parse("content://sms/"), projection, null, null, "date desc");
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
