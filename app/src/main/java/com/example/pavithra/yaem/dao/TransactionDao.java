package com.example.pavithra.yaem.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import com.example.pavithra.yaem.model.MonthlyReport;
import com.example.pavithra.yaem.persistence.TransactionAlert;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void add(List<TransactionAlert> transactions);

    @Query("select year, month, sum(credit) as totalCredit, sum(debit) as totalDebit from TransactionAlert group by year, month")
    List<MonthlyReport> getAggregatedMonthlyReport();

    @Query("select year, month, date, debit, credit from TransactionAlert where month= :month and year= :year")
    List<TransactionAlert> getTransactionAlerts(Integer month, Integer year);
}
