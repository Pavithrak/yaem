package com.example.pavithra.yaem.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sms {
    private String address;
    private String body;
    private Date date;
    private final List<Pattern> withdrawalRegex = new ArrayList<Pattern>() {{
        add(Pattern.compile("[d|D]ebited.*?(INR\\s|Rs.)(\\d+,?\\d+\\.?\\d*)"));
        add(Pattern.compile("of (INR\\s|Rs.)(\\d+,?\\d+\\.?\\d+?).*Credit Card"));
    }};
    private final List<Pattern> creditRegex = new ArrayList<Pattern>() {{
        add(Pattern.compile("[C|c]redited.*?(INR\\s|Rs.)(\\d+,?\\d+\\.?\\d*)"));
    }};
    private final List<Pattern> dateRegex = new ArrayList<Pattern>() {{
        add(Pattern.compile("(\\d{1,2}[-|\\/](\\d{1,2}|[a-zA-Z]{3})[-|\\/]\\d{2,4})"));
    }};
    private final List<String> dateFormats = new ArrayList<String>() {{
        add("dd-MM-yyyy");
        add("dd-MMM-yyyy");
        add("dd-MMM-yy");
        add("dd/MM/yyyy");
        add("dd/MMM/yyyy");
        add("dd/MMM/yy");
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

    public Double getWithdrawlAmount() {
        return this.getAmount(this.withdrawalRegex);
    }

    public Double getCreditedAmount() {
        return this.getAmount(this.creditRegex);
    }

    public Integer getTransactionMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getTransactionDate());
        return calendar.get(Calendar.MONTH) + 1;
    }

    public Integer getTransactionYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getTransactionDate());
        int year = calendar.get(Calendar.YEAR);
        return year < 100 ? year + 2000 : year;
    }

    public Integer getTransactionDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getTransactionDate());
        return calendar.get(Calendar.DATE);
    }

    public boolean isATransactionSms() {
        return this.getWithdrawlAmount() != null || this.getCreditedAmount() != null;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "address='" + address + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date + '\'' +
                ", getTransactionDate=" + this.getTransactionDate() + '\'' +
                ", getCredit =" + this.getCreditedAmount() + '\'' +
                ", getDebit =" + this.getWithdrawlAmount() + '\'' +
                '}';
    }

    private Date getTransactionDate() {
        Date transDate = null;
        for (Pattern pattern : this.dateRegex) {
            Matcher matcher = pattern.matcher(this.body);
            if (matcher.find()) {
                String date = matcher.group(1);
                transDate = convertStringToDate(date);
                break;
            }
        }
        return transDate == null ? this.date : transDate;
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

    private Date convertStringToDate(String date) {
        Date parsedDate = null;
        for (String dateFormat : this.dateFormats) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            try {
                parsedDate = format.parse(date);
                break;
            } catch (ParseException e) {
            }
        }
        return parsedDate;
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
}
