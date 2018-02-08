package com.example.pavithra.yaem.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class SmsTest {
    @Test
    public void shouldGetWithdrawnAmount() throws Exception {
        Sms sms = new Sms("AD-BANK",
                "Your a/c XX1234 is debited on 09/01/2018 by INR 3,144.95 towards Purchase. Avl Bal : INR 123. For more details login to m.sc.com/in - StanChart",
                new Date());
        Double withdrawalAmount = sms.getWithdrawlAmount();
        assertEquals(new Double(3144.95), withdrawalAmount);

        sms = new Sms("AD-BANK",
                "Tranx of INR 759.50 using Credit Card xxx1234 is made at VODAFONE-BILLDE on 10-JAN-18. Avbl Cr lmt:INR 63,488.50, Total Cr lmt: INR 70,000.00",
                new Date());
        withdrawalAmount = sms.getWithdrawlAmount();
        assertEquals(new Double(759.50),withdrawalAmount);

        sms = new Sms("AD-BANK",
                "Dear Customer, Your a/c no. XXXXXXXX1234 is credited by Rs.20,000.00 on 11-Jan-2018 12:41:56 by a/c linked to mobile XXXXX61234. (IMPS Ref no 12345).",
                new Date());

        withdrawalAmount = sms.getWithdrawlAmount();
        assertNull(withdrawalAmount);
    }

    @Test
    public void shouldGetCreditedAmount() throws Exception {
        Sms sms = new Sms("AD-BANK",
                "Dear Customer, Your a/c no. XXXXXXXX1234 is credited by Rs.20,000.10 on 11-Jan-2018 12:41:56 by a/c linked to mobile XXXXX61234. (IMPS Ref no 12345).",
                new Date());

        Double creditedAmount = sms.getCreditedAmount();
        assertEquals(new Double(20000.10), creditedAmount);

        sms = new Sms("AD-BANK",
                "Your account 123xxxx1234 has been credited on 15/01/2018 by INR 1,296.05.Available Balance:INR 123,001.26 -StanChart', date=Mon Jan 15 14:26:10 GMT+05:30 2018",
                new Date());

        creditedAmount = sms.getCreditedAmount();
        assertEquals(new Double(1296.05), creditedAmount);

        sms = new Sms("AD-BANK",
                "Tranx of INR 759.50 using Credit Card xxx1234 is made at VODAFONE-BILLDE on 10-JAN-18. Avbl Cr lmt:INR 63,488.50, Total Cr lmt: INR 70,000.00",
                new Date());

        creditedAmount = sms.getCreditedAmount();
        assertNull(creditedAmount);
    }

    @Test
    public void shouldGetWithdrawnDate() throws Exception {
        Sms sms = new Sms("AD-BANK",
                "Your a/c XX1234 is debited on 09/01/2018 by INR 3,144.95 towards Purchase. Avl Bal : INR 123. For more details login to m.sc.com/in - StanChart",
                new Date());
        assertEquals(2018, sms.getTransactionYear().intValue());
        assertEquals(1, sms.getTransactionMonth().intValue());
        assertEquals(9, sms.getTransactionDay().intValue());

        sms = new Sms("AD-BANK",
                "Tranx of INR 759.50 using Credit Card xxx1234 is made at VODAFONE-BILLDE on 10-JAN-18. Avbl Cr lmt:INR 63,488.50, Total Cr lmt: INR 70,000.00",
                new Date());
        assertEquals(2018, sms.getTransactionYear().intValue());
        assertEquals(1, sms.getTransactionMonth().intValue());
        assertEquals(10, sms.getTransactionDay().intValue());

        sms = new Sms("AD-BANK",
                "Dear Customer, Your a/c no. XXXXXXXX1234 is credited by Rs.20,000.00 on 11-Dec-2017 12:41:56 by a/c linked to mobile XXXXX61234. (IMPS Ref no 12345).",
                new Date());

        assertEquals(2017, sms.getTransactionYear().intValue());
        assertEquals(12, sms.getTransactionMonth().intValue());
        assertEquals(11, sms.getTransactionDay().intValue());
    }

    @Test
    public void shouldCheckForValidTransactionSms() throws Exception {
        Sms sms = new Sms("AD-BANK",
                "Your a/c XX1234 is debited on 09/01/2018 by INR 3,144.95 towards Purchase. Avl Bal : INR 123. For more details login to m.sc.com/in - StanChart",
                new Date());
        assertTrue(sms.isATransactionSms());

        sms = new Sms("AD-BANK",
                "Dear Customer, Your a/c no. XXXXXXXX1234 is credited by Rs.20,000.00 on 11-Dec-2017 12:41:56 by a/c linked to mobile XXXXX61234. (IMPS Ref no 12345).",
                new Date());
        assertTrue(sms.isATransactionSms());

        sms = new Sms("AD-BANK",
                "This is a random sms alert",
                new Date());
        assertFalse(sms.isATransactionSms());
    }

    @Test
    public void testPossibleCombinations() throws Exception {
        Sms sms = new Sms("AD-BANK",
                "Dear Customer, Your a/c no. XXXXXXXX6675 is debited for Rs.30,000.00 on 07-Feb-2018 09:26:27 and a/c XXXXXXXX4691 credited (IMPS Ref no 12345)",
                new Date());
        Double amount = sms.getWithdrawlAmount();
        assertEquals(new Double(30000), amount);
        assertEquals(2018, sms.getTransactionYear().intValue());
        assertEquals(2, sms.getTransactionMonth().intValue());
        assertEquals(7, sms.getTransactionDay().intValue());


        sms = new Sms("AD-BANK",
                "Dear Customer, your Account XX6675 has been debited with INR 30,000.00 on 07-Feb-18. Info: MMT*Ref803809040238*30600100. The Available Balance is INR 1,23,456.53.",
                new Date());
        amount = sms.getWithdrawlAmount();
        assertEquals(new Double(30000), amount);
        assertEquals(2018, sms.getTransactionYear().intValue());
        assertEquals(2, sms.getTransactionMonth().intValue());
        assertEquals(7, sms.getTransactionDay().intValue());


        sms = new Sms("AD-BANK",
                "Ac XXXXXXXX0001234 Debited with Rs.15000.00,05-02-2018 13:57:45 thru IBS. Aval Bal Rs.1234.74 CR. Avail PNB Mobile banking. Visit https://mobile.netpnb.com",
                new Date());
        amount = sms.getWithdrawlAmount();
        assertEquals(new Double(15000), amount);
        assertEquals(2018, sms.getTransactionYear().intValue());
        assertEquals(2, sms.getTransactionMonth().intValue());
        assertEquals(5, sms.getTransactionDay().intValue());


        sms = new Sms("AD-BANK",
                "Ac XXXXXXXX001234 Credited with Rs.2000.00,07-02-2018 15:01:26 thru NEFT from MS PAVITHRA KRISHNAN. Aval Bal 1234.94 CR Helpline 18001802222",
                new Date());
        amount = sms.getCreditedAmount();
        assertEquals(new Double(2000), amount);
        assertEquals(2018, sms.getTransactionYear().intValue());
        assertEquals(2, sms.getTransactionMonth().intValue());
        assertEquals(7, sms.getTransactionDay().intValue());

    }
}