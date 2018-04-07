package com.example.pavithra.yaem.service;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.example.pavithra.yaem.model.Sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmsService {
    public static List<Sms> readExistingSms(AppCompatActivity activity) {
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
