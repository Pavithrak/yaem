package com.example.pavithra.yaem.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class SmsTest {
    @Test
    public void shouldGetWithdrawnAmount() throws Exception {
        Sms sms = new Sms("AD-BANK",
                "Your a/c XX1234 is debited on 09/01/2018 by INR 3,144.95 towards Purchase. Avl Bal : INR 123456. For more details login to m.sc.com/in - StanChart",
                new Date());
        Double withdrawalAmount = sms.getWithdrawlAmount();
        assertEquals(new Double(3144.95), withdrawalAmount);

        sms = new Sms("AD-BANK",
                "Tranx of INR 759.50 using Credit Card xxx1234 is made at VODAFONE-BILLDE on 10-JAN-18. Avbl Cr lmt:INR 63,488.50, Total Cr lmt: INR 70,000.00",
                new Date());
        withdrawalAmount = sms.getWithdrawlAmount();
        assertEquals(new BigDecimal(759.50),withdrawalAmount);

        sms = new Sms("AD-BANK",
                "Dear Customer, Your a/c no. XXXXXXXX1234 is credited by Rs.20,000.00 on 11-Jan-2018 12:41:56 by a/c linked to mobile XXXXX61234. (IMPS Ref no 12345).",
                new Date());

        withdrawalAmount = sms.getWithdrawlAmount();
        assertEquals(new BigDecimal(0),withdrawalAmount);
    }

    @Test
    public void shouldGetCreditedAmount() throws Exception {

    }

    @Test
    public void shouldGetWithdrawnDate() throws Exception {

    }

    @Test
    public void shouldGetCategory() throws Exception {

    }
}