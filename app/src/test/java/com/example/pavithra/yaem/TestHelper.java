package com.example.pavithra.yaem;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class TestHelper {
    public static class MyLiveData <T> extends LiveData<T> {
        private T data;

        public MyLiveData(T t) {
            this.data = t;
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            observer.onChanged(data);
        }
    }

//    public static class TestAsyncTask <Params, Progress, Result> extends AsyncTask {
//        private Result data;
//
//        public TestAsyncTask(Result t) {
//            this.data = t;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            return null;
//        }
//    }

    public static class CurrentThreadExecutor implements Executor {
        public void execute(Runnable r) {
            r.run();
        }
    }
}
