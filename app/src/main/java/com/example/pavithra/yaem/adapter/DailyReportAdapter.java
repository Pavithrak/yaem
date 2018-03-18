package com.example.pavithra.yaem.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.activity.DailyReportActivity;
import com.example.pavithra.yaem.common.Utils;
import com.example.pavithra.yaem.persistence.TransactionAlert;

import java.util.HashMap;
import java.util.List;

public class DailyReportAdapter extends ArrayAdapter<TransactionAlert> {
    private DailyReportActivity activity;
    private int resource;
    private HashMap<Long, TransactionAlert> selectedItems;

    public DailyReportAdapter(Context context, int resource, List<TransactionAlert> items, DailyReportActivity activity) {
        super(context, resource, items);
        this.resource = resource;
        selectedItems = new HashMap<>();
        this.activity = activity;
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

        TextView monthText =  view.findViewById(R.id.date);
        monthText.setText(Utils.toString(report.getDate()) + "/" + Utils.toString(report.getMonth())+ "/" + Utils.toString(report.getYear()));

        TextView creditText =  view.findViewById(R.id.dailyCredit);
        creditText.setText(Utils.toString(report.getCredit()));

        TextView debitText =  view.findViewById(R.id.dailyDebit);
        debitText.setText(Utils.toString(report.getDebit()));

        view.setOnClickListener(new ReportClickListener(report));
        view.setOnLongClickListener(new ReportClickListener(report));
        return view;
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    private class ReportClickListener implements View.OnClickListener, View.OnLongClickListener {
        private TransactionAlert item;
        public ReportClickListener(TransactionAlert item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            if (activity.isActionModeActive()) {
                if( v.isSelected()) {
                    v.setSelected(false);
                    v.setBackgroundColor(Color.TRANSPARENT);
                    selectedItems.remove(item.getId());
                } else {
                    v.setSelected(true);
                    v.setBackgroundColor(0x9934B5E4);
                    selectedItems.put(item.getId(), item);
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            activity.activateActionMode();
            v.setSelected(true);
            v.setBackgroundColor(0x9934B5E4);
            selectedItems.put(item.getId(), item);
            return true;
        }
    }
}
