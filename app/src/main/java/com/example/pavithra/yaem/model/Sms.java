package com.example.pavithra.yaem.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class Sms {
    private String address;
    private String body;
    private Date date;
    private final Pattern debitSMS = Pattern.compile("debited|debit|purchase|Credit Card", CASE_INSENSITIVE);

    private final Pattern creditSMS = Pattern.compile("credit|credited", CASE_INSENSITIVE);

    private final Pattern creditIgnoreSMS = Pattern.compile("Credit Card", CASE_INSENSITIVE);

    private final Pattern otpSMS = Pattern.compile("OTP", CASE_INSENSITIVE);

    private final List<Pattern> amountRegex = new ArrayList<Pattern>() {{
        add(Pattern.compile(".*?(INR\\s|Rs[^\\d]?)(\\d+,?\\d+\\.?\\d*)"));
    }};

    public Sms(String address, String body, Date date) {
        this.address = address;
        this.body = body;
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public String getBody() {
        return body;
    }

    public Double getWithdrawalAmount() {
        Double amount = null;
        Matcher matcher = debitSMS.matcher(this.body);
        if (matcher.find()) {
            amount = this.getAmount(this.amountRegex);
        }
        return amount;
    }

    public Double getCreditedAmount() {
        Double amount = null;
        Matcher matcher = creditSMS.matcher(this.body);
        Matcher ignoreMatcher = creditIgnoreSMS.matcher(this.body);
        if (matcher.find() && !ignoreMatcher.find())  {
            amount = this.getAmount(this.amountRegex);
        }
        return amount;

    }

    public Integer getTransactionMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public Integer getTransactionYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        int year = calendar.get(Calendar.YEAR);
        return year < 100 ? year + 2000 : year;
    }

    public Integer getTransactionDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        return calendar.get(Calendar.DATE);
    }

    public boolean isATransactionSms() {
        return this.getWithdrawalAmount() != null || this.getCreditedAmount() != null;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "address='" + address + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date + '\'' +
                ", getTransactionDate=" + this.date + '\'' +
                ", getCredit =" + this.getCreditedAmount() + '\'' +
                ", getDebit =" + this.getWithdrawalAmount() + '\'' +
                '}';
    }

    private Double getAmount(List<Pattern> patterns) {
        Double value = null;
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(this.body);
            if (matcher.find()) {
                String numberString = matcher.group(2);
                value = convertStringToDouble(cleanse(numberString));
                break;
            }
        }
        return value;
    }

    private String cleanse(String value) {
        String trimmedValue = value.trim();
        return removeTrailingDot(trimmedValue);
    }

    private String removeTrailingDot(String trimmedValue) {
        Matcher matcher = Pattern.compile("(.*)\\.$").matcher(trimmedValue);
        return matcher.find() ? matcher.group(1) : trimmedValue;
    }

    private Double convertStringToDouble(String number) {
        Double value;
        try {
            value = NumberFormat.getInstance().parse(number).doubleValue();
        } catch (ParseException e) {
            value = null;
        }
        return value;
    }

    public boolean isAValidSms() {
        return isATransactionSms() && isNotAOTPSms();
    }

    public boolean isNotAOTPSms() {
        Matcher matcher = otpSMS.matcher(this.body);
        return !matcher.find();
    }
}
