package vip.lovek.interview.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-25 15:14
 */
public class UploadLogWorker extends Worker {

    public UploadLogWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("TAG", "doWork");
        //接收外面传递进来的数据
        String inputData = getInputData().getString("input_data");
        // 任务执行完成后返回数据
        Data outputData = new Data.Builder().putString("output_data", "Task Success!").build();

        return Result.success(outputData);
    }
}
