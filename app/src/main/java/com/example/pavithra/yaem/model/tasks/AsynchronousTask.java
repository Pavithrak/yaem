package com.example.pavithra.yaem.model.tasks;

import com.example.pavithra.yaem.AppDatabase;

public interface AsynchronousTask<R> {
    R execute(AppDatabase appDatabase);
}
