package com.example.pavithra.yaem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.activity.DailyReportActivity;
import com.example.pavithra.yaem.common.Utils;
import com.example.pavithra.yaem.model.MonthYear;
import com.example.pavithra.yaem.model.MonthlyReport;

import java.util.List;

public class MonthlyReportAdapter extends ArrayAdapter<MonthlyReport> {
    private int resource;

    public MonthlyReportAdapter(Context context, int resource, List<MonthlyReport> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        MonthlyReport report = getItem(position);

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(resource, null);
            view.setOnClickListener(new itemClickedListener(report));
        }

        TextView monthText =  view.findViewById(R.id.month_year);
        monthText.setText(Utils.toString(report.getMonth())+ "/" + Utils.toString(report.getYear()));

        TextView creditText =  view.findViewById(R.id.credit);
        creditText.setText(Utils.toString(report.getTotalCredit()));

        TextView debitText =  view.findViewById(R.id.debit);
        debitText.setText(Utils.toString(report.getTotalDebit()));

        return view;
    }

    private class itemClickedListener implements View.OnClickListener {
        private MonthlyReport report;

        public itemClickedListener(MonthlyReport report) {
            this.report = report;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), DailyReportActivity.class);
            intent.putExtra("monthYear", new MonthYear(report.getYear(), report.getMonth()));
            getContext().startActivity(intent);
        }
    }
}
