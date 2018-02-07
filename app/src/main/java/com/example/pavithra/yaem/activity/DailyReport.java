package com.example.pavithra.yaem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.adapter.DailyReportAdapter;
import com.example.pavithra.yaem.adapter.MonthlyReportAdapter;
import com.example.pavithra.yaem.model.MonthYear;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.async.GetDailyReport;
import com.example.pavithra.yaem.service.async.GetMonthlyReport;

import java.util.List;

public class DailyReport extends AppCompatActivity {
    DailyReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        MonthYear monthYear = (MonthYear) getIntent().getSerializableExtra("monthYear");

        GetDailyReport getMonthlyReport = new GetDailyReport(this);
        getMonthlyReport.execute(monthYear);
    }

    public void updateReport(List<TransactionAlert> report) {
        adapter = new DailyReportAdapter(this, R.layout.monthly_report_row, report);
        ListView listView = findViewById(R.id.dailyReportList);
        listView.setAdapter(adapter);
    }
}
