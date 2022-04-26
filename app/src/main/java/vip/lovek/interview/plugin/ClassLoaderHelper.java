package vip.lovek.interview.plugin;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import vip.lovek.interview.App;
import vip.lovek.interview.utils.ThreadPoolHandler;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-22 20:12
 */
public class ClassLoaderHelper {
    private static DexClassLoader dexClassLoader;

    private ClassLoaderHelper() {
    }

    public static DexClassLoader getDexClassLoader() {
        if (dexClassLoader == null) {
            synchronized (ClassLoaderHelper.class) {
                if (dexClassLoader == null) {
                    Log.e("TAG","getDexClassLoader new ...");
                    File apk = new File(App.getContext().getCacheDir() + "/pluginapp-debug.apk");
                    dexClassLoader = new DexClassLoader(apk.getPath(), App.getContext().getCacheDir().getPath(), null, null);
                    // okio 复制文件
                    ThreadPoolHandler.getInstance().run(() -> copyFile(apk));
                }
            }
        }
        return dexClassLoader;
    }

    private static void copyFile(File apk) {
        try (Source source = Okio.source(App.getContext().getAssets().open("pluginapp-debug.apk"));
             BufferedSink sink = Okio.buffer(Okio.sink(apk))) {
            sink.writeAll(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
