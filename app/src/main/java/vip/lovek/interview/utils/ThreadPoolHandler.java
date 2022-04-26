package vip.lovek.interview.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * description: 线程池
 * author： zryu@g2link.cn
 * date: 2018-02-12 18:03
 */

public class ThreadPoolHandler {
    private final ExecutorService mFixedThreadExecutor;
    private ScheduledExecutorService mScheduledExecutorService;
    private Handler mHandler;
    private static ThreadPoolHandler mThreadPoolHandler;

    private ThreadPoolHandler() {
        mFixedThreadExecutor = Executors.newFixedThreadPool(5);
    }

    public static ThreadPoolHandler getInstance() {
        if (mThreadPoolHandler == null) {
            mThreadPoolHandler = new ThreadPoolHandler();
        }
        return mThreadPoolHandler;
    }

    public void run(Runnable runnable) {
        mFixedThreadExecutor.execute(runnable);
    }

    public void run(Runnable runnable, long delay) {
        if (mScheduledExecutorService == null) {
            mScheduledExecutorService = Executors.newScheduledThreadPool(5);
        }
        mScheduledExecutorService.schedule(runnable, delay, TimeUnit.SECONDS);
    }

    public <T> void run(Callable<T> task, final UICallback<T> callback) {
        if (task == null) {
            return;
        }
        final Future<T> result = mFixedThreadExecutor.submit(task);
        run(() -> {
            try {
                final T t = result.get();
                if (callback != null) {
                    if (mHandler == null) {
                        mHandler = new Handler(Looper.getMainLooper());
                    }
                    mHandler.post(() -> callback.handler(t));
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.cancel(true);
            }
        });
    }

    public interface UICallback<T> {
        void handler(T t);
    }
}
