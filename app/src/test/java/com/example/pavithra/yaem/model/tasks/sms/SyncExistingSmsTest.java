package com.example.pavithra.yaem.model.tasks.sms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.dao.AccountDao;
import com.example.pavithra.yaem.dao.TransactionDao;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SyncExistingSmsTest {
    @Mock
    AppDatabase appDatabase;
    @Mock
    ContentResolver contentResolver;
    @InjectMocks
    SyncExistingSms syncExistingSms;

    @Test
    public void shouldReadAndFilterSmsBasedOnPresetAddress() throws Exception {
        SyncExistingSms spy = spy(syncExistingSms);
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        String[] projection = new String[]{"_id", "address", "body", "date"};
        List<TransactionAlert> transactionAlerts = asList(TransactionAlert.builder().build());
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
        when(mockAccountDao.getAccounts()).thenReturn(asList(new Account("address1")));

        List<Sms> allSms = spy.readExistingSms();

        Sms sms = allSms.get(0);
        assertEquals(2, allSms.size());
        assertEquals("address1", sms.getAddress());
        assertEquals("body1", sms.getBody());
        sms = allSms.get(1);
        assertEquals("address2", sms.getAddress());
        assertEquals("body2", sms.getBody());
    }

    @Test
    public void shouldCreateTransactionOnPostExecute() throws Exception {
        AccountDao mockAccountDao = mock(AccountDao.class);
        when(appDatabase.accountDao()).thenReturn(mockAccountDao);
        when(mockAccountDao.getAccounts()).thenReturn(asList(new Account(1l, "AD-SOMEBANK", null),
                new Account(2l, "AD-SOMEOTHERBANK", null)));
        SyncExistingSms spy = spy(syncExistingSms);
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        Sms sms = new Sms("AD-SOMEBANK", "debited by Rs.90 on 19-Dec-2017", new Date());
        Sms sms1 = new Sms("AD-SOMEOTHERBANK", "credited by INR 190 on 20-Dec-2017", new Date());
        Sms sms2 = new Sms("AD-SOMEOTHERBANK", "Some random sms", new Date());
        Sms sms3 = new Sms("AD-SOMERANDOMBANK", "credited by INR 190 on 20-Dec-2017", new Date());
        TransactionDao mockTransactionDao = mock(TransactionDao.class);
        when(appDatabase.transactionDao()).thenReturn(mockTransactionDao);
        doReturn(asList(sms, sms1, sms2, sms3)).when(spy).readExistingSms();

        spy.doInBackground();

        verify(mockTransactionDao).add(captor.capture());

        List transactionAlerts = captor.getValue();
        assertEquals(2, transactionAlerts.size());
        TransactionAlert transactionAlert = (TransactionAlert) transactionAlerts.get(0);
        assertEquals(1l, transactionAlert.getAccountId().longValue());
        assertEquals(new Double(90), transactionAlert.getDebit());
        assertEquals(12, transactionAlert.getMonth().intValue());
        assertEquals(2017, transactionAlert.getYear().intValue());
        assertEquals(19, transactionAlert.getDate().intValue());

        transactionAlert = (TransactionAlert) transactionAlerts.get(1);
        assertEquals(2l, transactionAlert.getAccountId().longValue());
        assertEquals(new Double(190), transactionAlert.getCredit());
        assertEquals(12, transactionAlert.getMonth().intValue());
        assertEquals(2017, transactionAlert.getYear().intValue());
        assertEquals(20, transactionAlert.getDate().intValue());
    }
}