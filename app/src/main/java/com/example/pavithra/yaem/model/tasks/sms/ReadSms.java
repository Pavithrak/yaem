package com.example.pavithra.yaem.model.tasks.sms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadSms extends AsyncTask<Void, Void, List<Sms>> {
    ContentResolver contentResolver;
    AppDatabase appDatabase;

    public ReadSms(AppDatabase appDatabase, ContentResolver contentResolver) {
        this.appDatabase = appDatabase;
        this.contentResolver = contentResolver;
    }

    @Override
    protected List<Sms> doInBackground(Void... voids) {
        return this.readMatchingSms();
    }


    @Override
    protected void onPostExecute(List<Sms> filteredSms) {
        super.onPostExecute(filteredSms);
        System.out.println("The size is " + filteredSms.size());
        for(Sms sms : filteredSms) {
            System.out.print("The sms is " + sms);
        }
    }

    private List<Sms> readMatchingSms() {
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
        return appDatabase.accountDao().getAccountNames();
    }
}
