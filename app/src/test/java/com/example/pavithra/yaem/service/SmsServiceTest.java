package com.example.pavithra.yaem.service;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.example.pavithra.yaem.TestHelper;
import com.example.pavithra.yaem.dao.AccountDao;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.service.async.SyncNewAccountSms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SmsServiceTest {
    @Test
    public void shouldReadAndFilterSmsBasedOnPresetAddress() throws Exception {
        AppCompatActivity activity = Mockito.mock(AppCompatActivity.class);
        ContentResolver mockContentResolver = mock(ContentResolver.class);

        String[] projection = new String[]{"_id", "address", "body", "date"};
        Cursor mockCursor = mock(Cursor.class);
        AccountDao mockAccountDao = mock(AccountDao.class);
        when(mockCursor.moveToFirst()).thenReturn(true);
        when(mockCursor.moveToNext()).thenReturn(true).thenReturn(false);
        when(mockCursor.getColumnIndex("address")).thenReturn(0);
        when(mockCursor.getColumnIndex("body")).thenReturn(1);
        when(mockCursor.getColumnIndex("date")).thenReturn(2);
        when(mockCursor.getString(0)).thenReturn("address1").thenReturn("address2");
        when(mockCursor.getString(1)).thenReturn("body1").thenReturn("body2");
        when(mockCursor.getLong(2)).thenReturn(12345l).thenReturn(12345l);
        when(activity.getContentResolver()).thenReturn(mockContentResolver);
        when(mockContentResolver.query(any(Uri.class), eq(projection), anyString(), any(String[].class), eq("date desc"))).thenReturn(mockCursor);
        final List<Account> accounts = asList(new Account("address1"));
        when(mockAccountDao.getAccountsData()).thenReturn(new TestHelper.MyLiveData<List<Account>>(accounts));

        List<Sms> allSms = SmsService.readExistingSms(activity);

        Sms sms = allSms.get(0);
        assertEquals(2, allSms.size());
        assertEquals("address1", sms.getAddress());
        assertEquals("body1", sms.getBody());
        sms = allSms.get(1);
        assertEquals("address2", sms.getAddress());
        assertEquals("body2", sms.getBody());
    }


}
