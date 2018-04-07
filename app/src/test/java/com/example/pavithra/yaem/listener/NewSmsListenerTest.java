package com.example.pavithra.yaem.listener;

import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.activity.MonthlyReportActivity;
import com.example.pavithra.yaem.dao.AccountDao;
import com.example.pavithra.yaem.dao.TransactionDao;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.TransactionAlertService;
import com.example.pavithra.yaem.service.async.SyncAllSms;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewSmsListenerTest {
    @Mock
    MonthlyReportActivity activity;
    @InjectMocks
    NewSmsListener newSmsListener;

    NewSmsListener spyListener;

    List<Sms> allSms;

    @Before
    public void setUp() throws Exception {
        Sms sms = new Sms("AD-SOMEBANK", "debited by Rs.90 on 19-Dec-2017", new Date());
        Sms sms1 = new Sms("AD-SOMEOTHERBANK", "credited by INR 190 on 20-Dec-2017", new Date());
        allSms = asList(sms, sms1);
        spyListener = spy(newSmsListener);
    }

    public AppDatabase getMockAppDatabase() {
        AppDatabase mockAppDatabase = mock(AppDatabase.class);
        AccountDao mockAccountDao = mock(AccountDao.class);
        TransactionDao mockTransactionDao = mock(TransactionDao.class);

        doReturn(mockAppDatabase).when(spyListener).getAppDatabase(Matchers.any(Context.class));
        when(mockAppDatabase.accountDao()).thenReturn(mockAccountDao);
        when(mockAppDatabase.transactionDao()).thenReturn(mockTransactionDao);

        return mockAppDatabase;
    }


    @Test
    public void shouldMapToTransactionOnReceivingNewSms() throws Exception {
        AppDatabase mockAppDatabase = getMockAppDatabase();
        SyncAllSms mockSyncAllSms = mock(SyncAllSms.class);


        final List<Account> accounts = asList(new Account(1l, "AD-SOMEBANK", null),
                new Account(2l, "AD-SOMEOTHERBANK", null));

        AccountDao mockAccountDao = mockAppDatabase.accountDao();
        when(mockAccountDao.getAccounts()).thenReturn(accounts);

        Intent mockIntent = mock(Intent.class);
        when(mockIntent.getAction()).thenReturn(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        doReturn(allSms).when(spyListener).getNewSms(mockIntent);
        doReturn(mockSyncAllSms).when(spyListener).getSyncAllSms(mockAppDatabase, allSms);

        spyListener.onReceive(mock(Context.class), mockIntent);

        verify(mockSyncAllSms).execute();
    }
}