package com.example.pavithra.yaem.service;

import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;

import java.util.ArrayList;
import java.util.List;

public class SmsService {
    private List<Sms> smsList;
    private List<Account> accounts;

    public SmsService(List<Sms> smsList, List<Account> accounts) {
        this.smsList = smsList;
        this.accounts = accounts;
    }

    public List<TransactionAlert> getFilteredTransactionAlerts() {
        List<TransactionAlert> transactionAlerts = new ArrayList<>();
        List<String> accountNames = getAccountNames();
        for(Sms sms : smsList) {
            if(isValidTransactionAlert(accountNames, sms)) {
                Long accountId = this.getAccountId(sms.getAddress());
                transactionAlerts.add(new TransactionAlert(accountId, sms.getBody(), sms.getWithdrawlAmount(),
                        sms.getCreditedAmount(), sms.getTransactionMonth(),
                        sms.getTransactionYear(), sms.getTransactionDay()));
            }
        }
        return transactionAlerts;
    }

    private boolean isValidTransactionAlert(List<String> accountNames, Sms sms) {
        return accountNames.contains(sms.getAddress()) && sms.isATransactionSms();
    }

    private List<String> getAccountNames() {
        List<String> accountNames = new ArrayList<>();
        for(Account account : this.accounts) {
            accountNames.add(account.getName());
        }
        return accountNames;
    }

    private Long getAccountId(String name) {
        Long accountId = null;
        for(Account account : this.accounts) {
            if (account.getName().equals(name)) {
                accountId = account.getId();
                break;
            }
        }
        return accountId;
    }

}
