package com.example.pavithra.yaem.service;

import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.persistence.TransactionAlert;

import java.util.ArrayList;
import java.util.List;

public class TransactionAlertService {
    private List<Sms> smsList;
    private List<Account> accounts;

    public TransactionAlertService(List<Sms> smsList, List<Account> accounts) {
        this.smsList = smsList;
        this.accounts = accounts;
    }

    public List<TransactionAlert> getFilteredTransactionAlerts() {
        List<TransactionAlert> transactionAlerts = new ArrayList<>();
        for(Sms sms : smsList) {
            if(sms.isAValidSms()) {
                Long accountId = this.getAccountId(sms.getAddress());
                transactionAlerts.add(new TransactionAlert(accountId, sms.getBody(), sms.getWithdrawalAmount(),
                        sms.getCreditedAmount(), sms.getTransactionMonth(),
                        sms.getTransactionYear(), sms.getTransactionDay()));
            }
        }
        return transactionAlerts;
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
