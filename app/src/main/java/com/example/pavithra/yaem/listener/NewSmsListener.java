package com.example.pavithra.yaem.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.SmsService;
import com.example.pavithra.yaem.service.async.GetAccounts;
import com.example.pavithra.yaem.service.async.AsyncTaskCallback;

import java.util.ArrayList;
import java.util.List;

public class NewSmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppDatabase appDatabase = getAppDatabase(context);
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            List<Sms> smsList = getNewSms(intent);
            GetAccounts getAccounts = getAccountsAsync(appDatabase, smsList);
            getAccounts.execute();
        }
    }

    @NonNull
    GetAccounts getAccountsAsync(AppDatabase appDatabase, List<Sms> smsList) {
        GetAccounts getAccounts = new GetAccounts(new AccountsCallback(appDatabase, smsList), appDatabase);
        return getAccounts;
    }

    SmsService getSmsService(List<Sms> smsList, List<Account> accounts) {
        return new SmsService(smsList, accounts);
    }

    AppDatabase getAppDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }

    List<Sms> getNewSms(Intent intent) {
        List<Sms> smsList = new ArrayList<>();
        for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            String address = smsMessage.getDisplayOriginatingAddress();
            String messageBody = smsMessage.getMessageBody();
            smsList.add(new Sms(address, messageBody, null));
        }
        return smsList;
    }

    private class AccountsCallback implements AsyncTaskCallback<List<Account>> {
        private AppDatabase appDatabase;
        private List<Sms> newSms;

        public AccountsCallback(AppDatabase appDatabase, List<Sms> smsList) {
            this.appDatabase = appDatabase;
            this.newSms = smsList;
        }

        @Override
        public void onSuccessBackground(List<Account> accounts) {
            List<TransactionAlert> transactionAlerts = getSmsService(newSms, accounts).getFilteredTransactionAlerts();
            appDatabase.transactionDao().add(transactionAlerts);
        }

        @Override
        public void onSuccess(List<Account> accounts) {}
    }
}
