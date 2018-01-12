package com.example.pavithra.yaem.model.tasks.sms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.dao.AccountDao;
import com.example.pavithra.yaem.model.Sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReadSmsTest {
    @Mock
    AppDatabase appDatabase;
    @Mock
    ContentResolver contentResolver;
    @InjectMocks
    ReadSms readSms;

    @Test
    public void shouldReadAndFilterSmsBasedOnPresetAddress() throws Exception {
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
        when(contentResolver.query(any(Uri.class), eq(projection), anyString(), any(String[].class), eq("date desc"))).thenReturn(mockCursor);
        when(appDatabase.accountDao()).thenReturn(mockAccountDao);
        when(mockAccountDao.getAccountNames()).thenReturn(asList("address1"));

        List<Sms> sms = readSms.doInBackground();

        assertEquals(1, sms.size());
        assertEquals("address1", sms.get(0).getAddress());
    }
}