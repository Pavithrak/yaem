package com.example.pavithra.yaem.dao;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.AggregatedMonthlyReport;
import com.example.pavithra.yaem.persistence.TransactionAlert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TransactionDaoTest {
    private TransactionDao transactionDao;
    private AppDatabase appDatabase;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        transactionDao = appDatabase.transactionDao();
    }

    @Test
    public void shouldGetMonthlyReport() throws Exception {
        TransactionAlert transactionAlert = TransactionAlert.builder().credit(new Double(90)).month(1).year(2017).build();
        TransactionAlert transactionAlert1 = TransactionAlert.builder().credit(new Double(80)).month(1).year(2017).build();
        TransactionAlert transactionAlert4 = TransactionAlert.builder().debit(new Double(190)).month(1).year(2017).build();
        TransactionAlert transactionAlert5 = TransactionAlert.builder().debit(new Double(170)).month(1).year(2017).build();
        TransactionAlert transactionAlert2 = TransactionAlert.builder().credit(new Double(80)).month(1).year(2016).build();
        TransactionAlert transactionAlert3 = TransactionAlert.builder().credit(new Double(90)).month(2).year(2017).build();
        TransactionAlert transactionAlert6 = TransactionAlert.builder().debit(new Double(140)).month(2).year(2017).build();
        TransactionAlert transactionAlert7 = TransactionAlert.builder().debit(new Double(150)).month(2).year(2016).build();
        List<TransactionAlert> transactionAlerts = asList(transactionAlert, transactionAlert1, transactionAlert2,
                transactionAlert3, transactionAlert4, transactionAlert5, transactionAlert6, transactionAlert7);
        transactionDao.add(transactionAlerts);

        List<AggregatedMonthlyReport> aggregatedMonthlyReport = transactionDao.getAggregatedMonthlyReport();

        assertEquals(4, aggregatedMonthlyReport.size());
        AggregatedMonthlyReport report = findAggregationFor(aggregatedMonthlyReport, 1, 2017);
        assertEquals(new Double(170), report.getTotalCredit());
        assertEquals(new Double(360), report.getTotalDebit());

        report = findAggregationFor(aggregatedMonthlyReport, 1, 2016);
        assertEquals(new Double(80), report.getTotalCredit());
        assertNull(report.getTotalDebit());

        report = findAggregationFor(aggregatedMonthlyReport, 2, 2017);
        assertEquals(new Double(90), report.getTotalCredit());
        assertEquals(new Double(140), report.getTotalDebit());

        report = findAggregationFor(aggregatedMonthlyReport, 2, 2016);
        assertNull(report.getTotalCredit());
        assertEquals(new Double(150), report.getTotalDebit());
    }

    private AggregatedMonthlyReport findAggregationFor(List<AggregatedMonthlyReport> aggregatedMonthlyReports, Integer month, Integer year) {
        AggregatedMonthlyReport matchingReport = null;
        for(AggregatedMonthlyReport report : aggregatedMonthlyReports) {
            if (month.equals(report.getMonth()) && year.equals(report.getYear())) {
                matchingReport = report;
                break;
            }
        }
        return matchingReport;
    }

    @After
    public void tearDown() throws Exception {
        appDatabase.close();
    }
}