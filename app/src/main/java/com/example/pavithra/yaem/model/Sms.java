package com.example.pavithra.yaem.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sms {
    private String address;
    private String body;
    private Date date;
    private final List<Pattern> withdrawalRegex = new ArrayList<Pattern>() {{
        add(Pattern.compile("debited .* by (INR\\s|Rs.)(\\d[^\\s]*)"));
        add(Pattern.compile("of (INR\\s|Rs.)(\\d[^\\s]*).*Credit Card"));
    }};
    private final List<Pattern> creditRegex = new ArrayList<Pattern>() {{
        add(Pattern.compile("credited by (INR\\s|Rs.)(\\d[^\\s]*)"));
    }};

    public Sms(String address, String body, Date date) {
        this.address = address;
        this.body = body;
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "address='" + address + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }

    public Double getWithdrawlAmount() {
        return this.getAmount(this.withdrawalRegex);
    }

    public Double getCreditedAmount() {
        return this.getAmount(this.creditRegex);
    }

    private Double getAmount(List<Pattern> patterns) {
        Double value = null;
        for(Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(this.body);
            if(matcher.find()) {
                String numberString = matcher.group(2);
                value = convertStringToDouble(numberString);
                break;
            }
        }
        return value;
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
