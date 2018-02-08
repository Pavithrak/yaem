package com.example.pavithra.yaem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.adapter.AccountsAdapter;
import com.example.pavithra.yaem.adapter.MonthlyReportAdapter;
import com.example.pavithra.yaem.model.MonthlyReport;
import com.example.pavithra.yaem.service.async.GetMonthlyReport;

import java.util.List;

public class MonthlyReportActivity extends AppCompatActivity {
    MonthlyReportAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouped_expense);
        GetMonthlyReport getMonthlyReport = new GetMonthlyReport(this);
        getMonthlyReport.execute();
    }

    public void updateReport(List<MonthlyReport> report) {
        adapter = new MonthlyReportAdapter(this, R.layout.monthly_report_row, report);
        ListView listView = findViewById(R.id.monthlyReportList);
        listView.setAdapter(adapter);
    }

    public void openAddAccountActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), AddAccountActivity.class);
        startActivity(intent);
    }
}
