package com.example.pavithra.yaem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.R;
import com.example.pavithra.yaem.persistence.Account;
import com.example.pavithra.yaem.service.async.DeleteAccount;

import java.util.List;

public class AccountsAdapter extends ArrayAdapter<Account> {
    AppDatabase appDatabase;
    DeleteAccount deleteAccount;
    public AccountsAdapter(Context context, int resource, List<Account> items, DeleteAccount deleteAccount) {
        super(context, resource, items);
        appDatabase = AppDatabase.getInstance(getContext());
        this.deleteAccount = deleteAccount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.account_row, null);

        }

        Account account = getItem(position);
        ImageButton deleteButton = view.findViewById(R.id.deleteAccount);
        deleteButton.setOnClickListener(new DeleteButtonListener(account));
        TextView tt1 =  view.findViewById(R.id.accName);
        tt1.setText(account.getName());

        return view;
    }

    private class DeleteButtonListener implements View.OnClickListener {
        private Account account;

        public DeleteButtonListener(Account account) {
            this.account = account;
        }

        @Override
        public void onClick(View v) {
            deleteAccount.execute(account);
            remove(account);
            notifyDataSetChanged();
        }
    }
}
