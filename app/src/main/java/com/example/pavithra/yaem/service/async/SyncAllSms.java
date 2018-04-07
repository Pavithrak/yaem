package com.example.pavithra.yaem.service.async;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.AccountService;
import com.example.pavithra.yaem.service.SmsService;
import com.example.pavithra.yaem.service.TransactionAlertService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SyncAllSms extends AsyncTask<Void, Void, List> {
    private List<Sms> sms;
    private AppDatabase appDatabase;
    private AccountService accountService;

    public SyncAllSms(List<Sms> sms, AppDatabase appDatabase) {
        this.sms = sms;
        this.appDatabase = appDatabase;
        this.accountService = new AccountService(appDatabase);

    }

    @Override
    protected List doInBackground(Void... voids) {
        List<Account> accounts = this.accountService.addAccounts(sms);
        List<TransactionAlert> transactionAlerts = new TransactionAlertService(sms, accounts).getFilteredTransactionAlerts();
        saveTransactionAlerts(transactionAlerts);
        return sms;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        for (Object obj : list) {
            Sms sms = (Sms) obj;
            if (!sms.isAValidSms()) {
                System.out.println("This invalid sms is " + sms);
            }
        }
    }

    void saveTransactionAlerts(List<TransactionAlert> transactionAlerts) {
        appDatabase.transactionDao().add(transactionAlerts);
    }
}
