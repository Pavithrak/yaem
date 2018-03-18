package com.example.pavithra.yaem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.widget.ListView;

import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.adapter.DailyReportAdapter;
import com.example.pavithra.yaem.model.MonthYear;
import com.example.pavithra.yaem.persistence.TransactionAlert;
import com.example.pavithra.yaem.service.async.GetDailyReport;

import java.util.List;

public class DailyReportActivity extends AppCompatActivity {
    DailyReportAdapter adapter;
    ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        MonthYear monthYear = (MonthYear) getIntent().getSerializableExtra("monthYear");
        GetDailyReport getMonthlyReport = new GetDailyReport(this);
        getMonthlyReport.execute(monthYear);
    }

    public void updateReport(List<TransactionAlert> report) {
        adapter = new DailyReportAdapter(this, R.layout.daily_report_row, report, this);
        ListView listView = findViewById(R.id.dailyReportList);
        listView.setAdapter(adapter);
    }

    public void activateActionMode() {
        if (actionMode == null) {
            actionMode = this.startActionMode(new DailyReportActionMode(this));
        }
    }

    public void clearSelections() {
        actionMode = null;
        adapter.clearSelections();
    }

    public boolean isActionModeActive() {
        return actionMode != null;
    }
}
