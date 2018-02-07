package com.example.pavithra.yaem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.common.Utils;
import com.example.pavithra.yaem.model.MonthlyReport;

import java.util.List;

public class MonthlyReportAdapter extends ArrayAdapter<MonthlyReport> {

    public MonthlyReportAdapter(Context context, int resource, List<MonthlyReport> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.monthly_report_row, null);

        }

        MonthlyReport report = getItem(position);
        TextView monthText =  view.findViewById(R.id.month);
        monthText.setText(Utils.toString(report.getMonth())+ "/" + Utils.toString(report.getYear()));

        TextView creditText =  view.findViewById(R.id.credit);
        creditText.setText(Utils.toString(report.getTotalCredit()));

        TextView debitText =  view.findViewById(R.id.debit);
        debitText.setText(Utils.toString(report.getTotalDebit()));

        return view;
    }
}
