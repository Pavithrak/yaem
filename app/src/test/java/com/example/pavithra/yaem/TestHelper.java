package com.example.pavithra.yaem;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

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
}
