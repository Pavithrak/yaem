package com.example.pavithra.yaem.service.async;

import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.activity.DailyReportActivity;
import com.example.pavithra.yaem.model.MonthYear;
import com.example.pavithra.yaem.persistence.TransactionAlert;

import java.util.List;

public class GetDailyReport extends AsyncTask<MonthYear, Void, List<TransactionAlert>> {
    AppDatabase appDatabase;
    DailyReportActivity activity;

    public GetDailyReport(DailyReportActivity activity) {
        this.activity = activity;
        this.appDatabase = AppDatabase.getInstance(activity.getApplicationContext());
    }

    @Override
    protected void onPostExecute(List<TransactionAlert> report) {
        super.onPostExecute(report);
        activity.updateReport(report);

    }

    @Override
    protected List<TransactionAlert> doInBackground(MonthYear... monthYears) {
        MonthYear monthYear = monthYears[0];
        return appDatabase.transactionDao().getTransactionAlerts(monthYear.getMonth(), monthYear.getYear());
    }
}
