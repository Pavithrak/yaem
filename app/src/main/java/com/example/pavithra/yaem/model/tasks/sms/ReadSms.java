package com.example.pavithra.yaem.model.tasks.sms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.model.tasks.AsynchronousTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadSms implements AsynchronousTask<List<Sms>> {
    ContentResolver contentResolver;

    public ReadSms(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }
    @Override
    public List<Sms> execute(AppDatabase appDatabase) {
        List<Sms> sms = new ArrayList();
        String[] projection = new String[]{"_id", "address", "body", "date"};
        String where = "address in ("+this.getAccountNames(appDatabase)+")";
        Cursor cur = contentResolver.query(Uri.parse("content://sms/"), projection, null, null, "date desc");
        System.out.print("The length is " + cur.getCount());
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

    private String getAccountNames(AppDatabase appDatabase) {
        List<String> accountNames = appDatabase.accountDao().getAccountNames();
        List<String> nameWithQuotes = new ArrayList<>();
        for(String name : accountNames) {
            nameWithQuotes.add("'" + name + "'");
        }
        return TextUtils.join(",", nameWithQuotes);
    }
}
