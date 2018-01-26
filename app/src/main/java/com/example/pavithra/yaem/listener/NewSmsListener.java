package com.example.pavithra.yaem.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.SmsService;

import java.util.ArrayList;
import java.util.List;

public class NewSmsListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppDatabase appDatabase = getAppDatabase(context);
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            List<Account> accounts = appDatabase.accountDao().getAccounts();
            List<Sms> smsList = getNewSms(intent);
            List<TransactionAlert> transactionAlerts = getSmsService(smsList, accounts).getFilteredTransactionAlerts();
            appDatabase.transactionDao().add(transactionAlerts);
        }
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
}
