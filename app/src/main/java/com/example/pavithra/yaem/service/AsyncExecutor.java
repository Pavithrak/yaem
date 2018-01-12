package com.example.pavithra.yaem.service;

import android.os.AsyncTask;

import com.example.pavithra.yaem.AppDatabase;
import com.example.pavithra.yaem.model.tasks.AsynchronousTask;

public class AsyncExecutor<R> extends AsyncTask<AsynchronousTask<R>, Void, R> {
    private AppDatabase appDatabase;

    public AsyncExecutor(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected R doInBackground(AsynchronousTask<R>... tasks) {
        AsynchronousTask<R>[] allTasks = tasks;
        return allTasks[0].execute(appDatabase);
    }

    @Override
    protected void onPostExecute(R r) {
        super.onPostExecute(r);

    }
}
