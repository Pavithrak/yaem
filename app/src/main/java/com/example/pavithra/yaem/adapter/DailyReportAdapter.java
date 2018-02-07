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
import com.example.pavithra.yaem.persistence.TransactionAlert;

import java.util.List;

public class DailyReportAdapter extends ArrayAdapter<TransactionAlert> {
    private int resource;

    public DailyReportAdapter(Context context, int resource, List<TransactionAlert> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        TransactionAlert report = getItem(position);

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(resource, null);
        }

        TextView monthText =  view.findViewById(R.id.month_year);
        monthText.setText(Utils.toString(report.getDate()) + "/" + Utils.toString(report.getMonth())+ "/" + Utils.toString(report.getYear()));

        TextView creditText =  view.findViewById(R.id.credit);
        creditText.setText(Utils.toString(report.getCredit()));

        TextView debitText =  view.findViewById(R.id.debit);
        debitText.setText(Utils.toString(report.getDebit()));

        return view;
    }

}
