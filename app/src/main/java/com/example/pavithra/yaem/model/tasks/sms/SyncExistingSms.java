package com.example.pavithra.yaem.model.tasks.sms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SyncExistingSms extends AsyncTask<Void, Void, Void> {
    private ContentResolver contentResolver;
    private AppDatabase appDatabase;
    private List<Account> accounts;

    public SyncExistingSms(AppDatabase appDatabase, ContentResolver contentResolver) {
        this.appDatabase = appDatabase;
        this.contentResolver = contentResolver;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        this.accounts = appDatabase.accountDao().getAccounts();
        List<Sms> sms = this.readMatchingSms();
        List<TransactionAlert> transactionAlerts = mapToTransaction(sms);
        saveTransactionAlerts(transactionAlerts);
        return null;
    }

    void saveTransactionAlerts(List<TransactionAlert> transactionAlerts) {
        appDatabase.transactionDao().add(transactionAlerts);
    }

    List<TransactionAlert> mapToTransaction(List<Sms> filteredSms) {
        List<TransactionAlert> transactionAlerts = new ArrayList<>();
        for(Sms sms : filteredSms) {
            Long accountId = this.getAccountId(sms.getAddress());
            transactionAlerts.add(new TransactionAlert(accountId, sms.getBody(), sms.getWithdrawlAmount(),
                    sms.getCreditedAmount(), sms.getTransactionMonth(),
                    sms.getTransactionYear(), sms.getTransactionDay()));
        }
        return transactionAlerts;
    }

    List<Sms> readMatchingSms() {
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
        return this.filter(sms, this.getAccountNames());
    }

    private List<Sms> filter(List<Sms> allSms, List<String> accountNames) {
        List<Sms> filteredSms = new ArrayList<>();
        for(Sms sms : allSms) {
            if(accountNames.contains(sms.getAddress())) {
                filteredSms.add(sms);
            }
        }
        return filteredSms;
    }

    private List<String> getAccountNames() {
        List<String> accountNames = new ArrayList<>();
        for(Account account : this.accounts) {
            accountNames.add(account.getName());
        }
        return accountNames;
    }

    private Long getAccountId(String name) {
        Long accountId = null;
        for(Account account : this.accounts) {
            if (account.getName().equals(name)) {
                accountId = account.getId();
                break;
            }
        }
        return accountId;
    }
}
