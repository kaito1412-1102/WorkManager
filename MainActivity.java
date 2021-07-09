package com.example.workmanager.updateSeekbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.workmanager.R;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView txtWork1, txtWork2, txtWork3;
    Constraints constraints;
    OneTimeWorkRequest workRequest1, workRequest2, workRequest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initViews();
        constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();
        work1();
        work2();
        work3();
        WorkManager.getInstance(this).beginWith(Arrays.asList(workRequest1, workRequest2))
                .then(workRequest3)
                .enqueue();

    }

    private void work1() {
        Data data = new Data.Builder()
                .putInt("number", 10)
                .build();
        workRequest1 = new OneTimeWorkRequest.Builder(DownloadWorker.class)
                .setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.SECONDS)
                .addTag("download")
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest1.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState().isFinished()) {
                    Data data1 = workInfo.getOutputData();
                    int result = data1.getInt("number", -1);
                    txtWork1.setText(String.valueOf(result));
                } else if (workInfo != null) {
                    Data progress = workInfo.getProgress();
                    int value = progress.getInt("progress", 0);
                    txtWork1.setText(String.valueOf(value));
                }
            }
        });
    }

    private void work2() {
        workRequest2 = new OneTimeWorkRequest.Builder(DownloadWorker.class).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest2.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState().isFinished()) {
                    Data data1 = workInfo.getOutputData();
                    int result = data1.getInt("number", -1);
                    txtWork2.setText(String.valueOf(result));
                } else if (workInfo != null) {
                    Data progress = workInfo.getProgress();
                    int value = progress.getInt("progress", 0);
                    txtWork2.setText(String.valueOf(value));
                }
            }
        });
    }

    private void work3() {
        workRequest3 = new OneTimeWorkRequest.Builder(DownloadWorker.class).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest3.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState().isFinished()) {
                    Data data1 = workInfo.getOutputData();
                    int result = data1.getInt("number", -1);
                    txtWork3.setText(String.valueOf(result));
                } else if (workInfo != null) {
                    Data progress = workInfo.getProgress();
                    int value = progress.getInt("progress", 0);
                    txtWork3.setText(String.valueOf(value));
                }
            }
        });
    }

    private void initViews() {
        txtWork1 = findViewById(R.id.txt_work1);
        txtWork2 = findViewById(R.id.txt_work2);
        txtWork3 = findViewById(R.id.txt_work3);
    }
}