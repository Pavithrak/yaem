package com.example.pavithra.yaem.activity;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pavithra.yaem.R;

public class DailyReportActionMode implements ActionMode.Callback {
    private DailyReportActivity activity;

    public DailyReportActionMode(DailyReportActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Toast toast = Toast.makeText(activity.getApplicationContext(), "Ignore is clicked", Toast.LENGTH_LONG);
        toast.show();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.activity.clearSelections();
    }
}
