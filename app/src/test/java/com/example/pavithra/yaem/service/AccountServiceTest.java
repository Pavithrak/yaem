package com.example.pavithra.yaem.service;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.dao.AccountDao;
import com.example.pavithra.yaem.model.Sms;
import com.example.pavithra.yaem.persistence.Account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    AppDatabase appDatabase;
    @InjectMocks
    AccountService accountService;
    @Test
    public void shouldAddAccounts() throws Exception {
        AccountDao mockAccountDao = mock(AccountDao.class);
        AccountService spyService = spy(accountService);

        List<String> accounts = new ArrayList<>();
        accounts.add("myacc");
        accounts.add("myacc1");
        Sms sms = new Sms("myacc", "debited by Rs.90 on 19-Dec-2017", new Date());
        Sms sms1 = new Sms("AD-SOMEOTHERBANK", "credited by INR 190 on 20-Dec-2017", new Date());
        List<Sms> allSms = asList(sms, sms1);
        when(appDatabase.accountDao()).thenReturn(mockAccountDao);
        doReturn(accounts).when(spyService).getAccountNames();

        spyService.addAccounts(allSms);

        verify(mockAccountDao).getAccounts();


    }
}
