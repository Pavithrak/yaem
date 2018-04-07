package com.example.pavithra.yaem.service;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;

import java.util.List;

public class AccountService {
    private AppDatabase database;

    public AccountService(AppDatabase database) {
        this.database = database;

    }

    public List<Account> addAccounts(List<Sms> sms) {
        List<String> accountNames = this.getAccountNames();
        for(Sms aSms : sms) {
            if (aSms.isAValidSms() && !accountNames.contains(aSms.getAddress())) {
                database.accountDao().add(new Account(aSms.getAddress()));
                accountNames.add(aSms.getAddress());
            }
        }
        return database.accountDao().getAccounts();
    }

    List<String> getAccountNames() {
        return database.accountDao().getAccountNames();
    }


}
