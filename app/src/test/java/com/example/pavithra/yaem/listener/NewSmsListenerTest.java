package com.example.pavithra.yaem.listener;

import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.dao.AccountDao;
import com.example.pavithra.yaem.dao.TransactionDao;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.SmsService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewSmsListenerTest {
    @InjectMocks
    NewSmsListener newSmsListener;
    @Test
    public void shouldMapToTransactionOnReceivingNewSms() throws Exception {
        NewSmsListener spyListener = spy(newSmsListener);
        SmsService mockSmsService = mock(SmsService.class);
        AppDatabase mockAppDatabase = mock(AppDatabase.class);
        doReturn(mockAppDatabase).when(spyListener).getAppDatabase(any(Context.class));

        List<Account> accounts = asList(new Account(1l, "AD-SOMEBANK", null),
                new Account(2l, "AD-SOMEOTHERBANK", null));
        AccountDao mockAccountDao = mock(AccountDao.class);
        when(mockAppDatabase.accountDao()).thenReturn(mockAccountDao);
        when(mockAccountDao.getAccounts()).thenReturn(accounts);

        Intent mockIntent = mock(Intent.class);
        when(mockIntent.getAction()).thenReturn(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);

        Sms sms = new Sms("AD-SOMEBANK", "debited by Rs.90 on 19-Dec-2017", new Date());
        Sms sms1 = new Sms("AD-SOMEOTHERBANK", "credited by INR 190 on 20-Dec-2017", new Date());
        List<Sms> allSms = asList(sms, sms1);
        doReturn(mockSmsService).when(spyListener).getSmsService(allSms, accounts);
        doReturn(allSms).when(spyListener).getNewSms(mockIntent);
        List<TransactionAlert> transactionAlerts = asList(TransactionAlert.builder().build());
        when(mockSmsService.getFilteredTransactionAlerts()).thenReturn(transactionAlerts);

        TransactionDao mockTransactionDao = mock(TransactionDao.class);
        when(mockAppDatabase.transactionDao()).thenReturn(mockTransactionDao);


        spyListener.onReceive(mock(Context.class), mockIntent);

        verify(mockTransactionDao).add(transactionAlerts);
    }
}