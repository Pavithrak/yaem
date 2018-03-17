package com.example.pavithra.yaem.service.async;

public interface AsyncTaskCallback<T>{
    public void onSuccess(T t);
    public void onSuccessBackground(T t);
}
