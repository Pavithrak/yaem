package com.example.pavithra.yaem.service.async;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.SmsService;
import com.example.pavithra.yaem.service.TransactionAlertService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SyncNewAccountSms extends AsyncTask<Account, Void, List> {
    private AppCompatActivity activity;
    private AppDatabase appDatabase;

    public SyncNewAccountSms(AppCompatActivity activity) {
        this.activity = activity;
        this.appDatabase = AppDatabase.getInstance(activity.getApplicationContext());
    }

    public SyncNewAccountSms(AppCompatActivity activity, AppDatabase appDatabase) {
        this.activity = activity;
        this.appDatabase = appDatabase;
    }

    @Override
    protected List<Sms> doInBackground(Account... accounts) {
        List<Sms> sms = getExistingSms();
        List<Sms> filteredSms = this.filterSmsOfAccount(sms, accounts);
        List<TransactionAlert> transactionAlerts = getTransactionService(filteredSms, accounts).getFilteredTransactionAlerts();
        saveTransactionAlerts(transactionAlerts);
        return sms;
    }

    List<Sms> getExistingSms() {
        return SmsService.readExistingSms(activity);
    }

    private List<Sms> filterSmsOfAccount(List<Sms> sms, Account[] accounts) {
        List<Sms> filteredList = new ArrayList<>();
        List<String> accountNameList = this.getAccountNames(accounts);
        for(Sms aSms : sms) {
            if (accountNameList.contains(aSms.getAddress())) {
                filteredList.add(aSms);
            }
        }
        return filteredList;
    }

    private List<String> getAccountNames(Account[] accounts) {
        List<String> names = new ArrayList<>();
        List<Account> accountsList = Arrays.asList(accounts);
        for(Account account : accountsList) {
            names.add(account.getName());
        }
        return names;
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

    TransactionAlertService getTransactionService(List<Sms> sms, Account[] accounts) {
        return new TransactionAlertService(sms, Arrays.asList(accounts));
    }
}
