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
import com.example.pavithra.yaem.service.SmsService;

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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        spyListener = Mockito.spy(newSmsListener);
    }

    public AppDatabase getMockAppDatabase() {
        AppDatabase mockAppDatabase = Mockito.mock(AppDatabase.class);
        AccountDao mockAccountDao = Mockito.mock(AccountDao.class);
        TransactionDao mockTransactionDao = Mockito.mock(TransactionDao.class);

        Mockito.doReturn(mockAppDatabase).when(spyListener).getAppDatabase(Matchers.any(Context.class));
        Mockito.when(mockAppDatabase.accountDao()).thenReturn(mockAccountDao);
        Mockito.when(mockAppDatabase.transactionDao()).thenReturn(mockTransactionDao);

        return mockAppDatabase;
    }


    @Test
    @Ignore
    public void shouldMapToTransactionOnReceivingNewSms() throws Exception {
        AppDatabase mockAppDatabase = getMockAppDatabase();


        final List<Account> accounts = asList(new Account(1l, "AD-SOMEBANK", null),
                new Account(2l, "AD-SOMEOTHERBANK", null));

        AccountDao mockAccountDao = mockAppDatabase.accountDao();
        Mockito.when(mockAccountDao.getAccounts()).thenReturn(accounts);

        Intent mockIntent = Mockito.mock(Intent.class);
        Mockito.when(mockIntent.getAction()).thenReturn(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        Mockito.doReturn(allSms).when(spyListener).getNewSms(mockIntent);


        List<TransactionAlert> transactionAlerts = asList(TransactionAlert.builder().build());
        SmsService spySmsService = Mockito.mock(SmsService.class);
        Mockito.doReturn(spySmsService).when(spyListener).getSmsService(allSms, accounts);
        Mockito.when(spySmsService.getFilteredTransactionAlerts()).thenReturn(transactionAlerts);

//        runTestOnUiThread()
        spyListener.onReceive(Mockito.mock(Context.class), mockIntent);

        TransactionDao mockTransactionDao = mockAppDatabase.transactionDao();
        Mockito.verify(mockTransactionDao).add(transactionAlerts);
    }
}