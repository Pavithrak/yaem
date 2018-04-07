package com.example.pavithra.yaem.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.service.async.SyncAllSms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewSmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppDatabase appDatabase = getAppDatabase(context);
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            List<Sms> smsList = getNewSms(intent);
            SyncAllSms syncAllSms = getSyncAllSms(appDatabase, smsList);
            syncAllSms.execute();
        }
    }

    @NonNull
    SyncAllSms getSyncAllSms(AppDatabase appDatabase, List<Sms> smsList) {
        return new SyncAllSms(smsList, appDatabase);
    }

    AppDatabase getAppDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }

    List<Sms> getNewSms(Intent intent) {
        List<Sms> smsList = new ArrayList<>();
        for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            String address = smsMessage.getDisplayOriginatingAddress();
            String messageBody = smsMessage.getMessageBody();
            smsList.add(new Sms(address, messageBody, new Date(smsMessage.getTimestampMillis())));
        }
        return smsList;
    }
}
