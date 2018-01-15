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
                "Dear Customer, Your a/c no. XXXXXXXX1234 is credited by Rs.20,000.00 on 11-Jan-2018 12:41:56 by a/c linked to mobile XXXXX61234. (IMPS Ref no 12345).",
                new Date());

        Double creditedAmount = sms.getCreditedAmount();
        assertEquals(new Double(20000), creditedAmount);

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
}