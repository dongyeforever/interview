package vip.lovek.interview.plugin.ams;

import android.util.Log;

/**
 * author： yuzhirui@douban.com
 * date: 2022-04-14 09:13
 */
public class Test {
    public void hello() {
        long start = System.currentTimeMillis();
        System.out.println("hello");
        long end = System.currentTimeMillis();
        Log.i("benz", this.getClass().getSimpleName() + " ----> OnCreate execute cost " + (end - start) + "ms");
    }
}
