package com.example.pavithra.yaem.service;

import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SmsServiceTest {

    @Test
    public void shouldMapAndFilterTransactions() throws Exception {
        Sms sms = new Sms("AD-SOMEBANK", "debited by Rs.90 on 19-Dec-2017", new Date());
        Sms sms1 = new Sms("AD-SOMEOTHERBANK", "credited by INR 190 on 20-Dec-2017", new Date());
        Sms incorrectSms2 = new Sms("AD-SOMEOTHERBANK", "Some random sms", new Date());
        Sms smsFromDifferentBank = new Sms("AD-SOMERANDOMBANK", "credited by INR 190 on 20-Dec-2017", new Date());
        Account account = new Account(1l, "AD-SOMEBANK", null);
        Account account1 = new Account(2l, "AD-SOMEOTHERBANK", null);
        SmsService smsService = new SmsService(asList(sms, sms1, incorrectSms2, smsFromDifferentBank), asList(account, account1));

        List<TransactionAlert> transactionAlerts = smsService.getFilteredTransactionAlerts();

        assertEquals(2, transactionAlerts.size());
        TransactionAlert transactionAlert = transactionAlerts.get(0);
        assertEquals(1l, transactionAlert.getAccountId().longValue());
        assertEquals(new Double(90), transactionAlert.getDebit());
        assertEquals(12, transactionAlert.getMonth().intValue());
        assertEquals(2017, transactionAlert.getYear().intValue());
        assertEquals(19, transactionAlert.getDate().intValue());

        transactionAlert = transactionAlerts.get(1);
        assertEquals(2l, transactionAlert.getAccountId().longValue());
        assertEquals(new Double(190), transactionAlert.getCredit());
        assertEquals(12, transactionAlert.getMonth().intValue());
        assertEquals(2017, transactionAlert.getYear().intValue());
        assertEquals(20, transactionAlert.getDate().intValue());
    }
}