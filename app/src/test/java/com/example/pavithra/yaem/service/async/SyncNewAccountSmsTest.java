package com.example.pavithra.yaem.service.async;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.TestHelper.MyLiveData;
import com.example.pavithra.yaem.dao.AccountDao;
import com.example.pavithra.yaem.dao.TransactionDao;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.TransactionAlertService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SyncNewAccountSmsTest {
    @Mock
    AppCompatActivity activity;
    @Mock
    AppDatabase appDatabase;
    @InjectMocks
    SyncNewAccountSms syncNewAccountSms;

    @Test
    public void shouldCreateTransaction() throws Exception {
        SyncNewAccountSms spy = spy(syncNewAccountSms);

        List<Account> accounts = asList(new Account(1l, "AD-SOMEBANK", null),
                new Account(2l, "AD-SOMEOTHERBANK", null));

        TransactionAlertService mockTransactionService = mock(TransactionAlertService.class);
        List<TransactionAlert> transactionAlerts = asList(TransactionAlert.builder().build());
        when(mockTransactionService.getFilteredTransactionAlerts()).thenReturn(transactionAlerts);

        Sms sms = new Sms("AD-SOMEBANK", "debited by Rs.90 on 19-Dec-2017", new Date());
        Sms sms1 = new Sms("AD-SOMEOTHERBANK", "credited by INR 190 on 20-Dec-2017", new Date());
        Sms sms2 = new Sms("AD-SOMEOTHERBANK", "Some random sms", new Date());
        Sms sms3 = new Sms("AD-SOMERANDOMBANK", "credited by INR 190 on 20-Dec-2017", new Date());
        List<Sms> allSms = asList(sms, sms1, sms2, sms3);

        TransactionDao mockTransactionDao = mock(TransactionDao.class);
        when(appDatabase.transactionDao()).thenReturn(mockTransactionDao);

        doReturn(allSms).when(spy).getExistingSms();
        doReturn(mockTransactionService).when(spy).getTransactionService(asList(sms, sms1, sms2), (Account[]) accounts.toArray());

        spy.doInBackground((Account[]) accounts.toArray());

        verify(mockTransactionDao).add(transactionAlerts);
    }
}