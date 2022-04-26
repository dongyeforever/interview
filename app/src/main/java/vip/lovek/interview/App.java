package vip.lovek.interview;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.tencent.mmkv.MMKV;

import vip.lovek.annotation.AsmTimer;
import vip.lovek.interview.base.ActivityManager;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-17 16:09
 */
public class App extends MultiDexApplication {
    private static App app;

    @AsmTimer
    public static Context getContext() {
        return app.getApplicationContext();
    }

    @Override
    @AsmTimer
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        app = this;

//        try {
//            AMSManager.hookAms(this);
//            AMSManager.hookActivityThread(this);
//        } catch (Exception e) {
//            Log.e("Main", "加载异常了 = " + e.getMessage());
//            e.printStackTrace();
//        }

        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
    }


    @Override
    @AsmTimer
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(ActivityManager.callbacks);
    }
}
