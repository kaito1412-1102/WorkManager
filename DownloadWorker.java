package com.example.workmanager.updateSeekbar;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DownloadWorker extends Worker {
    private static final String TAG = "DownloadWorker";

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        setProgressAsync(new Data.Builder().putInt("progress", 0).build());
    }

    @NonNull
    @Override
    public Result doWork() {
        Data inputData = getInputData();
        int number = inputData.getInt("number", 10);
        for (int i = 0; i < number; i++) {
            Log.d(TAG, "doWork: " + i);
            setProgressAsync(new Data.Builder().putInt("progress", i).build());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return Result.failure();
            }
        }
        Data outputData = new Data.Builder()
                .putInt("number", 10)
                .build();
        return Result.success(outputData);
    }
}
