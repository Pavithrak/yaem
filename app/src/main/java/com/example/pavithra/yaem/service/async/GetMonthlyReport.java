package com.example.pavithra.yaem.service.async;

import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.activity.MonthlyReportActivity;
import com.example.pavithra.yaem.model.MonthlyReport;

import java.util.List;

public class GetMonthlyReport extends AsyncTask<Void, Void, List<MonthlyReport>> {
    AppDatabase appDatabase;
    MonthlyReportActivity activity;

    public GetMonthlyReport(MonthlyReportActivity activity) {
        this.activity = activity;
        this.appDatabase = AppDatabase.getInstance(activity.getApplicationContext());
    }

    @Override
    protected void onPostExecute(List<MonthlyReport> report) {
        super.onPostExecute(report);
        activity.updateReport(report);

    }

    @Override
    protected List<MonthlyReport> doInBackground(Void... voids) {
        return appDatabase.transactionDao().getAggregatedMonthlyReport();
    }
}
