package com.example.pavithra.yaem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.persistence.Account;

import java.util.List;

public class AccountsAdapter extends ArrayAdapter<Account> {

    public AccountsAdapter(Context context, int resource, List<Account> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.account_row, null);
        }

        Account p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.accName);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

        }
        return v;
    }

}
