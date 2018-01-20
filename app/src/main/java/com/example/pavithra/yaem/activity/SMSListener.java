package com.example.pavithra.yaem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.model.tasks.sms.SyncExistingSms;


public class SMSListener extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smslistener);
        SyncExistingSms syncExistingSmsTask = new SyncExistingSms(AppDatabase.getInstance(getApplicationContext()), getContentResolver());
        syncExistingSmsTask.execute();
    }

    public void startListening(View view) {
//        final String SMS_URI_INBOX = "content://sms/";
//        Uri uri = Uri.parse(SMS_URI_INBOX);
//        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
//        Cursor cur = getContentResolver().query(uri, projection, null, null, "date desc");
//        Integer totalCount = cur.getCount();
//        System.out.println("the count is " + totalCount);
//        if (cur.moveToFirst()) {
//            int index_Address = cur.getColumnIndex("address");
//            int index_Person = cur.getColumnIndex("person");
//            int index_Body = cur.getColumnIndex("body");
//            int index_Date = cur.getColumnIndex("date");
//            int index_Type = cur.getColumnIndex("type");
//            do {
//                String strAddress = cur.getString(index_Address);
//                int intPerson = cur.getInt(index_Person);
//                String strbody = cur.getString(index_Body);
//                long longDate = cur.getLong(index_Date);
//                int int_Type = cur.getInt(index_Type);
//               System.out.println("Address " + strAddress );
//               System.out.println("Body " + strbody );
//            } while (cur.moveToNext());
//            if (!cur.isClosed()) {
//                cur.close();
//                cur = null;
//            }
//        } else {
//            System.out.println("Nothing to print");
//        }
    }
}
